package service;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.json.JSONObject;

import model.User;

public class SessionService {

	public static boolean validateUser(String data, HttpHeaders httpHeaders) {
		System.out.println("SessionService::getUser");
		JSONObject json = new JSONObject(data);
		String userName1 = json.getString("username");
		String userName2 = LoginService.getUser(httpHeaders);
		if(!userName1.equals(userName2))
			return false;
		
		String password = UserService.getPassword(httpHeaders);
		List<User> resultados = ManagementService.getUsersListDAO().getByUsername(userName1);
		
		if(resultados.isEmpty())
			return false;
		
		if(resultados.size() == 1)
			if(resultados.get(0).getPassword().equals(password))
				return true;

		for (User user : resultados) {
			System.out.println(user.getUserName());
		}
		return false;
	}

}
