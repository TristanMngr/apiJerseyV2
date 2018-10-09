package service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.DatatypeConverter;

public class LoginService {

    private String getUser(HttpHeaders headers) {

        // this is a very minimalistic and "naive" code; if you plan to use it
        // add necessary checks (see org.glassfish.jersey.examples.httpsclientservergrizzly.authservergrizzly.SecurityFilter)

    	List<String> list = headers.getRequestHeader("Authorization");
    	for(int i = 0; i < list.size() ; i++) {
        	System.out.println("list[i] = " + list.get(i));
        }
    	
        String auth = headers.getRequestHeader("Authorization").get(0);
        System.out.println("auth = " + auth);
        auth = auth.substring("Basic ".length());
        // 123456 - YWRtaW46YWRtaW4=
        System.out.println("auth = " + auth);
        
        String[] values = new String(DatatypeConverter.parseBase64Binary(auth), Charset.forName("ASCII")).split(":");
        for(int i = 0; i < values.length ; i++) {
        	System.out.println("Values[i]" + values[i]);
        }
        
        // String username = values[0];
        // String password = values[1];

        return values[0];
    }
	
    static boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {
        boolean isAllowed = false;
          
        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
         
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
