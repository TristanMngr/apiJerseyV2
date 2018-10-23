package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.bson.types.ObjectId;

import java.util.List;
import javax.ws.rs.core.HttpHeaders;

import org.json.JSONObject;
import model.User;

public class UserService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String getUserById(ObjectId objectId) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getByUserId(objectId);
        if (user == null) {
            return null;
        }
        return mapper.writeValueAsString(ManagementService.getUserDAO().getByUserId(objectId));
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

    public static User create(String data, HttpHeaders headers) {
        System.out.println("UserService::create");
        JSONObject json     = new JSONObject(data);
        String     userName = json.getString("username");
        User       newUser  = new User(userName, getPassword(headers));
        ManagementService.getUsersListDAO().create(newUser);
        return newUser;
    }

    public static String getPassword(HttpHeaders headers) {
        System.out.println("UserService::getPassword");

        List<String> list = headers.getRequestHeader("Authorization");
        String       auth = headers.getRequestHeader("Authorization").get(0);
        auth = auth.substring("Basic ".length());

        return auth;
    }
}

