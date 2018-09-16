package controller;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;

@Path("/admin")
public class Admin {
	
	// TODO: Not yet implemented
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 @Path("/users/{userID}")
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response getUser(@PathParam("userID") String name) {
		 return Response.status(201).entity(new User(name)).build(); 
	 }
		 	
}
