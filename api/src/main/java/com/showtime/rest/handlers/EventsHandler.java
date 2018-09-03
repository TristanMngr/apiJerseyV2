package com.showtime.rest.handlers;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventsHandler {
	
	public static List<String> testListEvents = Arrays.asList("1", "2", "3"); 
	
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response get() {
		 List<String> result = testListEvents;
		 return Response.status(201).entity(result).build();
	 }

	 @Path("{eventID}")
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response produceJSON(@PathParam("eventID") int id) {
		 if(testListEvents.contains(String.valueOf(id)))
		 {
			 return Response.status(201).entity("Event: " + String.valueOf(id) + " was found!" ).build();
		 }
		 else
		 {
			 return Response.status(201).entity("Event: " + String.valueOf(id) + " not found!" ).build();
		 } 
	 }	
	
	// Not yet implemented
	public int getAmountEvents()
	{
		return 0;
	}

}
