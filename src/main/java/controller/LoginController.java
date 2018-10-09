package controller;

import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/login")
public class LoginController {
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Response login(@Context UriInfo uriInfo) {
		
		// Redirecciona a la pagina inicial si el login fue exitoso 
		
		URI uri = uriInfo.getBaseUriBuilder().build();
	    return Response.seeOther(uri).build();
	}
	
}
