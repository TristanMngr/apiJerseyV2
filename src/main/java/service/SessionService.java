package service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import model.Session;
import model.User;

public class SessionService {

	public static boolean createSession(String username, String token) {
		
		System.out.println("SessionService::createSession");
		User user = ManagementService.getUserDAO().getUserByName(username);
		
		if(user == null ) {
			System.out.println("SessionService::createSession - Could not find any user");
			return false;
		}
			
		Session sesion = new Session(user,token, new Date());
		ManagementService.getSessionListDAO().save(sesion);
		ManagementService.getUserDAO().updateLastLogin(username);
		return true;
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
        
        User user = ManagementService.getUserDAO().getUserByName(userFromCookie);
        
        if(user == null)
        {
        	System.out.println("SessionService::validateSession - No user found");
        }
        
        boolean found = false;
		
		Session session = ManagementService.getSessionListDAO().getSessionByUserWithToken(user, tokenFromCookie);
		if(session != null)
			found = true;


		return found;
	}

	public static void deleteSession(String username, String token) {
		User user = ManagementService.getUserDAO().getUserByName(username);
		ManagementService.getSessionListDAO().deleteSessionByUserWithToken(user, token);
	}
}
