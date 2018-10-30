package service;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

import model.User;

public class LoginService {

	public static String getToken()  {
		System.out.println("LoginService::getToken");	
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		SecureRandom random = new SecureRandom();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			float aleatorio = random.nextFloat();
			double roundedOneDigitX = Math.round(aleatorio * 100) / 10.0;
			int firstDecimal = (int) roundedOneDigitX;
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			char myCharacter = (char) randomLimitedInt; 
			if(firstDecimal % 2 == 0) {
				randomLimitedInt = random.nextInt(10);
				myCharacter = Character.forDigit(randomLimitedInt, 10);
			}

			if(firstDecimal > 5)
				myCharacter = Character.toUpperCase(myCharacter);
				
			buffer.append(myCharacter);
		}
		String generatedString = buffer.toString();
		return generatedString;   	
	}

	public static String getUserFromHeaders(HttpHeaders headers) {
		System.out.println("LoginService::getUser");
		if(headers.getRequestHeader("Authorization") == null) {
			System.out.println("LoginService::getUser - No hay authentication header");
			return "";
		}
			
		
		String auth = headers.getRequestHeader("Authorization").get(0);
		auth = auth.substring("Basic ".length());

		String[] values = new String(DatatypeConverter.parseBase64Binary(auth), Charset.forName("ASCII")).split(":");

		return values[0];
	}
	
	public static String getUserFromCookie(Map<String, Cookie> cookies) {
	
		System.out.println("LoginService::getUserFromCookie");
		String userFromCookie = "";

        for (Cookie c : cookies.values()) 
        {
            if (c.getName().equals("username")) {
            	userFromCookie = c.getValue();
            	break;
            }
        }
        
        if(userFromCookie.equals(""))
        {
        	System.out.println("SessionService::validateSession - username not present in the cookie");
        	return "";
        }  
		
		return userFromCookie;
	}
	
	public static String getTokenFromCookie(Map<String, Cookie> cookies) {
		System.out.println("LoginService::getUserFromCookie");
		String tokenFromCookie = "";

        for (Cookie c : cookies.values()) 
        {
            if (c.getName().equals("tokenG5")) {
            	tokenFromCookie = c.getValue();
            	break;
            }
        }
        
        if(tokenFromCookie.equals(""))
        {
        	System.out.println("SessionService::validateSession - TokenG5 not present in the cookie");
        	return "";
        }  
		
		return tokenFromCookie;
	}

	static boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
	{
		System.out.println("LoginService::isUserAllowed");
		boolean isAllowed = false;

		if(username.equals("howtodoinjava") && password.equals("password"))
		{
			String userRole = "ADMIN";

			//Step 2. Verify user role
			if(rolesSet.contains(userRole))
			{
				isAllowed = true;
			}
		}
		return isAllowed;
	}

	public static boolean validateUser(String data, HttpHeaders httpHeaders) {
		System.out.println("LoginService::validateUser");
		JSONObject json = new JSONObject(data);
		String userName1 = json.getString("username");
		String userName2 = LoginService.getUserFromHeaders(httpHeaders);
		if(!userName1.equals(userName2))
		{
			System.out.println("SessionService::validateUser - User on Header is not the same provided on Body");
			return false;
		}
			
		String password = UserService.getPassword(httpHeaders);
		User user = ManagementService.getUserDAO().getUserByName(userName1);
		
		if(user == null) {
			System.out.println("SessionService::validateUser - Could not find any user");
			return false;
		}
		
		if(!user.getPassword().equals(password))
		{
			System.out.println("SessionService::validateUser - Password does not match");
			return false;
		}
			
		return true;
	}



	
}
