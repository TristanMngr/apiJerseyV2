package service;

import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import org.json.JSONObject;
import model.User;

public class UserService {

	public static User create(String data, HttpHeaders headers ) {
		System.out.println("UserService::create");	
		JSONObject json = new JSONObject(data);
		String userName = json.getString("username");
		User newUser = new User(userName, getPassword(headers));
		ManagementService.getUsersListDAO().create(newUser);
		return newUser;
	}

	public static String getPassword(HttpHeaders headers) {
		System.out.println("UserService::getPassword");		

		List<String> list = headers.getRequestHeader("Authorization");
		String auth = headers.getRequestHeader("Authorization").get(0);
		auth = auth.substring("Basic ".length());

		return auth;
	}
	
	
}
