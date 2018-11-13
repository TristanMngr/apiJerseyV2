package controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import model.EventsList;
import model.User;
import service.EventsListsService;
import service.ManagementService;


@Path("/admin")
public class AdminController {

	@RolesAllowed("ADMIN")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getRole() {
		JSONObject obj = new JSONObject();
		obj.put("role", "admin");
		Response response = Response.ok().entity(obj.toString()).build();
		return response;
	}
	
	
	@Path("/users")
	@RolesAllowed("ADMIN")
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
	@RolesAllowed("ADMIN")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUser(@PathParam("userID") String name) {
		System.out.println(this.getClass().getName() + ":: AdminController getUser");
		User user = ManagementService.getUserDAO().getUserByName(name);

		JSONObject obj = new JSONObject();
		obj.put("username", user.getUserName());
		obj.put("cantListas", user.getEventsLists().size());
		obj.put("cantAlarmas", user.getAlarms().size());
		obj.put("lastLogin", user.getLastLogin());
		Response response = Response.ok().entity(obj.toString()).build();
		return response;

		//return Response.status(201).entity(user).build(); 
	}
	
	@Path("/users/{userID}/events")
	@GET
	@RolesAllowed("ADMIN")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUserLists(@PathParam("userID") String name) {
		System.out.println(this.getClass().getName() + ":: AdminController getUserLists");
		User user = ManagementService.getUserDAO().getUserByName(name);
		
		List<EventsList> events = user.getEventsLists();
		JSONArray jsonEvents = new JSONArray(events);
		
		Response response = Response.ok().entity(jsonEvents.toString()).build();
		return response;
	}
	
	@Path("/compare")
	@GET
	@RolesAllowed("ADMIN")
	@Produces({MediaType.APPLICATION_JSON})
	public Response compareUserLists(@QueryParam("user1") String user1, 
									@QueryParam("list1") String list1,
									@QueryParam("user2") String user2,
									@QueryParam("list2") String list2
									) {
		//TODO: Terminar comparacion de listados
		
		System.out.println(this.getClass().getName() + ":: AdminController compareUserLists");
		System.out.println(this.getClass().getName() + ":: user1 = " + user1);
		System.out.println(this.getClass().getName() + ":: list1 = " + list1);
		System.out.println(this.getClass().getName() + ":: user2 = " + user2);
		System.out.println(this.getClass().getName() + ":: list2  " + list2);
		
		List<Long> listado1 = EventsListsService.getListOfEventsByUserAndListName(user1, list1);
		List<Long> listado2 = EventsListsService.getListOfEventsByUserAndListName(user2, list2);
		
		Response response = Response.ok().entity("hola").build();
		return response;
	}

}
