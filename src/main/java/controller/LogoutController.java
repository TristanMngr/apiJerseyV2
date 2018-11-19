package controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.LoginService;
import service.SessionService;

@Path("/logout")
public class LogoutController {
	
	@PermitAll
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response logout(@Context HttpHeaders httpHeaders, String data) {
		
		System.out.println(this.getClass().getName() + "::logout");					
		String username = LoginService.getUserFromCookie(httpHeaders.getCookies());		
		String token =  LoginService.getTokenFromCookie(httpHeaders.getCookies());
		System.out.println(this.getClass().getName() + ":: username = " + username);
		System.out.println(this.getClass().getName() + ":: token = " + token);
		
		SessionService.deleteSession(username,token);

		Response response = Response.ok().build();
		return response;

	}
}
