package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import model.Session;
import model.User;

public class SessionService {

	public static boolean createSession(String username, String token) {
		
		System.out.println("SessionService::createSession");
		List<User> resultados = ManagementService.getUsersListDAO().getByUsername(username);
		
		if(resultados.isEmpty()) {
			System.out.println("SessionService::createSession - Could not find any user");
			return false;
		}
			
		
		if(resultados.size() == 1) {
			int userID = resultados.get(0).getUserId();
			Session sesion = new Session(userID,token, new Date());
			ManagementService.getSessionListDAO().create(sesion);
			return true;
		}
		return false;
	}

	public static boolean validateSession(Map<String, Cookie> cookies) {
		
		System.out.println("SessionService::validateSession");
		String tokenFromCookie = "";
		String userFromCookie = "";

        for (Cookie c : cookies.values()) 
        {
            if (c.getName().equals("tokenG5")) {
            	tokenFromCookie = c.getValue();
            }
            if (c.getName().equals("username")) {
            	userFromCookie = c.getValue();
            }
        }
        
        if(userFromCookie.equals(""))
        {
        	System.out.println("SessionService::validateSession - username not present in the cookie");
        	return false;
        }      
        
        if(tokenFromCookie.equals(""))
        {
        	System.out.println("SessionService::validateSession - username not present in the cookie");
        	return false;
        }   
        
        int userId = ManagementService.getUsersListDAO().getUserIdFromUsername(userFromCookie);
        
        if(userId == -1)
        {
        	System.out.println("SessionService::validateSession - No user found");
        }
        
        boolean found = false;
        
        for(int i = 0; i < ManagementService.getSessionListDAO().getListadoSesiones().size() ; i++) {
        	
        	Session sesion =  ManagementService.getSessionListDAO().getListadoSesiones().get(i);
        	if(sesion.getToken().equals(tokenFromCookie) && sesion.getUserId() == userId) {
        		found = true;
        	}
        	
        	// TODO: Remove previous sessions
        	
        }
        
		
		return found;
	}

}
