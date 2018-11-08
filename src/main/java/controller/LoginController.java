package controller;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import service.LoginService;
import service.SessionService;

@Path("/login")
public class LoginController {
	// SyntaxError: JSON.parse: unexpected character at line 2 column 1 of the JSON data
	private int MAX_AGE = 60 * 15;
	
	@PermitAll
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@Context HttpHeaders httpHeaders, String data) {
		
		System.out.println(this.getClass().getName() + "::login");
		// TODO: Agregar codigo de error a la pagina.
		// TODO: Revisar errores de password
		// TODO: Revisar errores en Firefox
		if(!LoginService.validateUser(data, httpHeaders))
			return Response.status(Response.Status.UNAUTHORIZED).entity("Your id couldn't be verified").build();
						
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date nuevaFecha = calendar.getTime();

		String username = LoginService.getUserFromHeaders(httpHeaders);
		
		System.out.println(this.getClass().getName() + ":: username = " + username);
		
		String token =  LoginService.getToken();
		System.out.println(this.getClass().getName() + ":: token = " + token);
		
		NewCookie tokenCookie = new NewCookie("tokenG5", token, "/", null, 0, "", MAX_AGE, nuevaFecha, false, false);
		NewCookie usernameCookie = new NewCookie("username", username, "/", null, 0, "", MAX_AGE, nuevaFecha, false, false);
		
		SessionService.createSession(username,token);
		
		
		Response response = Response.seeOther(URI.create("/")).cookie(tokenCookie).cookie(usernameCookie).build(); 
		return response;
		

	}
	
}
