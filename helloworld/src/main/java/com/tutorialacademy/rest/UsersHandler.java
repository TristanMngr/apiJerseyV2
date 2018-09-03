package com.tutorialacademy.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UsersHandler {
	
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public User get() {
		 System.out.println("LLegue aca");
		 return new User("Cristhian");
	 }
	 
	 
	 
	 
}
