package com.showtime.rest.handlers;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.showtime.rest.User;

@Path("/users")
public class UsersHandler {
	
	public static List<String> testListUsers = Arrays.asList("cristhian", "francisco", "guillermo", "juan"); 
	
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response get() {
		 List<String> result = testListUsers;
		 return Response.status(201).entity(result).build();
	 }

	 @Path("{userID}")
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response produceJSON(@PathParam("userID") String name) {
		 if(testListUsers.contains(name.toLowerCase()))
		 {
			 return Response.status(201).entity(new User(name)).build(); 
		 }
		 else
		 {
			 return Response.status(404).entity("User: " + name + " not found!" ).build();
		 } 
	 }
	 
}
