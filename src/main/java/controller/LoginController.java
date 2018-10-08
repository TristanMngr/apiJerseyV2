package controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

@Path("/login")
public class LoginController {
	
//	@POST
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	 public Response postUserInFORM(String string) {
//		 System.out.println("Hice un POST = " + string);
//		 return Response.status(200).entity(string).build();
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Response postUserInFORM(@Context HttpHeaders headers) {
        System.out.println("Service: POST / User: " + getUser(headers));
		String output = "<h1>Hello World!<h1>" +
				"<p>postUserInFORM</p<br>";
		return Response.status(200).entity(output).build();			
	}
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response postUserInJSON(@Context HttpHeaders headers) {
        System.out.println("Service: POST / User: " + getUser(headers));
		String output = "<h1>Hello World!<h1>" +
				"<p>postUserInJSON</p<br>";
		return Response.status(200).entity(output).build();			
	}

	
    @GET
    public String get1(@Context HttpHeaders headers) {
        // you can get username form HttpHeaders
    	System.out.println("Service: GET / User:");
    	System.out.println(getUser(headers));

        //return Server.CONTENT;
        return "Hola";
    }

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


}
