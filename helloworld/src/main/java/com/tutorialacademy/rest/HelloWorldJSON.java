package com.tutorialacademy.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/world")
public class HelloWorldJSON {
	
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public WorldState get() {
		 System.out.println("LLegue aca");
		 return new WorldState("fine");
	 }

}
