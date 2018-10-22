package controller;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import model.User;
import model.User_Old;
import service.ManagementService;
import service.UserService;

@Path("/users")
public class UsersController {


	//public List<String> testListUsers = Arrays.asList("cristhian", "francisco", "guillermo", "juan");

	public static List<String> testListUsers = new ArrayList<String>();

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response get() {
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
			return Response.status(201).entity(new User_Old(name)).build(); 
		}
		else
		{
			return Response.status(404).entity("User: " + name + " not found!" ).build();
		} 
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserInJSON(User_Old user) {
		System.out.println("Hice un PUT");
		String result = "Track saved : " + user;
		testListUsers.add(user.getName());
		return Response.status(201).entity(result).build();		
	}


	@POST
	public Response postUserInJSON(@Context HttpHeaders httpHeaders, String data) {
		System.out.println(this.getClass().getName() + ":: postUserInJSON ...");
		User newUser = UserService.create(data, httpHeaders);

		List<User> listado = ManagementService.getUsersListDAO().getListadoUsuarios();

		JSONObject obj = new JSONObject();
		obj.put("OPERATION", "GET");
		obj.put("URL", "/");
		
		Response response = Response.status(201).entity(obj.toString()).build();
		return response;
	}


}
