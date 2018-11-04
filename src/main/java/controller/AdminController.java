package controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import model.User;
import service.ManagementService;

@Path("/admin")
public class AdminController {

	@Path("/users")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUsers() {

		List<User> listado = ManagementService.getUserDAO().getAllUsers();

		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();  
		
		for (User user : listado) 
        {
            arr.put(user.getUserName());
        }	
		
		obj.put("users", arr);
		Response response = Response.ok().entity(obj.toString()).build();
		return response;
	}
	
	@Path("/users/{userID}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUser(@PathParam("userID") String name) {

		User user = ManagementService.getUserDAO().getUserByName(name);

		JSONObject obj = new JSONObject();
		obj.put("username", user.getUserName());
		obj.put("cantListas", user.getEventsLists().size());
		obj.put("cantAlarmas", user.getAlarmas().size());
		obj.put("lastLogin", user.getLastLogin());
		Response response = Response.ok().entity(obj.toString()).build();
		return response;

		//return Response.status(201).entity(user).build(); 
	}
	
	

}
