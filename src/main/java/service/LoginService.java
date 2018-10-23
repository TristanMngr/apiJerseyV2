package service;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.DatatypeConverter;

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

	public static String getUser(HttpHeaders headers) {
		System.out.println("LoginService::getUser");		

		List<String> list = headers.getRequestHeader("Authorization");
		String auth = headers.getRequestHeader("Authorization").get(0);
		auth = auth.substring("Basic ".length());

		String[] values = new String(DatatypeConverter.parseBase64Binary(auth), Charset.forName("ASCII")).split(":");

		return values[0];
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

}
