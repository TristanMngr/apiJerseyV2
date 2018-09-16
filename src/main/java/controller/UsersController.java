package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;


@Path("/users")
public class UsersController {
	
	//public List<String> testListUsers = Arrays.asList("cristhian", "francisco", "guillermo", "juan");
	
	public static List<String> testListUsers = new ArrayList<String>();

	
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public Response get() {
		 System.out.println("Get");
		if(testListUsers.size() == 0) {
			testListUsers.add("cristhian");
			testListUsers.add("francisco");
			testListUsers.add("guillermo");
			testListUsers.add("juan");
		}
		 
		 List<String> result = testListUsers;
		 return Response.status(201).entity(result).build();
	 }
	 
	 @OPTIONS
	 public Response getOptions() {
		 System.out.println("getOptions");
		 return Response.ok()
				 .header("Access-Control-Allow-Origin", "*")
				 .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				 .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
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
	 
	 @PUT
	 @Consumes(MediaType.APPLICATION_JSON)
	 public Response createUserInJSON(User user) {
		 System.out.println("Hice un PUT");
		 String result = "Track saved : " + user;
		 testListUsers.add(user.getName());
		 return Response.status(201).entity(result).build();		
	}
	
	 
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 public Response postUserInJSON(User user) {
		 System.out.println("Hice un POST");
		 String result = "Track saved : " + user;
		 testListUsers.add("*"+user.getName()+"*");
		 return Response.status(201).entity(result).build();		
	}
	 
	 
}
