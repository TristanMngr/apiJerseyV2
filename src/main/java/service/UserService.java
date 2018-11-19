package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import model.EventsList;

import org.json.JSONObject;

public class UserService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String getUserById(ObjectId objectId) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getByUserId(objectId);
        if (user == null) {
            return null;
        }
        return mapper.writeValueAsString(user);
    }

    public static String getUserByName(String userName) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getUserByName(userName);
        if (user == null) {
            return null;
        }
        return mapper.writeValueAsString(ManagementService.getUserDAO().getUserByName(userName));
    }

    public static String getAllUsers() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getUserDAO().getAllUsers());
    }
    
    public static String getAllNonAdminUsers() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getUserDAO().getAllNonAdminUsers());
    }

    public static User create(String data, HttpHeaders headers) {
        System.out.println("UserService::create");
        // TODO: Confirm if user can be created. We are not checking unique username
        JSONObject json = new JSONObject(data);
        String userName = json.getString("username");
        User newUser = new User(userName, getPassword(headers));
        newUser.setLastLogin(new Date());
        ManagementService.getUserDAO().save(newUser);
        return newUser;
    }

    public static String getPassword(HttpHeaders headers) {
        System.out.println("UserService::getPassword");

        List<String> list = headers.getRequestHeader("Authorization");
        String auth = headers.getRequestHeader("Authorization").get(0);
        auth = auth.substring("Basic ".length());

        return auth;
    }

    public static User getUserObjectById(String userId) {
        return ManagementService.getUserDAO().getByUserId(new ObjectId(userId));
    }

    public static List<EventsList> getListsByUser(String userId) {
        User user = getUserObjectById(userId);
        return user.getEventsLists();
    }

    public static User addListToUser(EventsList list, String userId) {
        User user = getUserObjectById(userId);
        List<EventsList> lists = getListsByUser(userId);
        lists.add(list);
        user.setEventsLists(lists);
        ManagementService.getUserDAO().saveEventsListsToUser(new ObjectId(userId), lists);
        return user;
    }

	public static boolean validateRole(Map<String, Cookie> cookies, String string) {
		System.out.println("UserService::validateRole");
	
		Cookie cookieUsername = cookies.get("username");
		String userFromCookie = cookieUsername.getValue();
		User user = ManagementService.getUserDAO().getUserByName(userFromCookie);
		
		if(user.getRole().equals(string)) {
			return true;
		}
	
		return false;
	}

    public static User currentUser(ContainerRequestContext containerRequestContext) {
        User user = null;

        for (Cookie c : containerRequestContext.getCookies().values())
        {
            if (c.getName().equals("username")) {
                user = ManagementService.getUserDAO().getUserByName(c.getValue());
            }
        }
        System.out.println(user.getUserName());
        return user;
    }
}
