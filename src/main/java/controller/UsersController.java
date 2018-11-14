package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.User;
import org.bson.types.ObjectId;

import service.AlarmService;
import service.ManagementService;
import service.UserService;
import org.json.JSONObject;

@Path("/users")
public class UsersController {
    public static List<String> testListUsers = new ArrayList<String>();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() throws JsonProcessingException {
        return Response.ok(UserService.getAllUsers()).build();
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
    public Response getUserById(@PathParam("userID") String userId) throws JsonProcessingException {
        ObjectId userObjectId = new ObjectId(userId);
        String user = UserService.getUserById(userObjectId);
        if (user != null) {
            return  Response.ok(user).build();
        } else {
            return Response.status(404).entity("User: " + userId + " not found!").build();
        }
    }


    @Path("name/{userName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByName(@PathParam("userName") String userName) throws JsonProcessingException {
        String user = UserService.getUserByName(userName);
        if (user != null) {
            return  Response.ok(UserService.getUserByName(userName)).build();
        } else {
            return Response.status(404).entity("User: " + userName + " not found!").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserInJSON(User user) {
        System.out.println("Hice un PUT");
        String result = "Track saved : " + user;
        testListUsers.add(user.getUserName());
        return Response.status(201).entity(result).build();
    }
   
    @PermitAll
	@POST
	public Response postUserInJSON(@Context HttpHeaders httpHeaders, String data) {
		System.out.println(this.getClass().getName() + ":: postUserInJSON ...");
		// TODO: Verificar que el usuario fue creado y evaluar respuesta.
		
		User newUser = UserService.create(data, httpHeaders);
		if(newUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity("Your user couldn't be created").build();
		
        JSONObject obj = new JSONObject();
		obj.put("OPERATION", "GET");
		obj.put("URL", "/");	
		Response response = Response.status(201).entity(obj.toString()).build();
		return response;
	}

    @Path("{userId}/alarms")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAlarmsFromUser(@PathParam("userId") String userId) throws JsonProcessingException {
        ObjectId userObjectId = new ObjectId(userId);
        User user = ManagementService.getUserDAO().getByUserId(userObjectId);
        if (user != null) {
            return  Response.ok().entity(AlarmService.getAlarmsFromUser(user)).build();
        } else {
            return Response.status(404).entity("User: " + userId + " not found!").build();
        }
    }

    @Path("/currentUserAlarm")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCurrentUserAlarms(@CookieParam("username") String username) throws JsonProcessingException {
        User user = ManagementService.getUserDAO().getUserByName(username);
        if (user != null) {
            return  Response.ok().entity(AlarmService.getAlarmsFromUser(user)).build();
        } else {
            return Response.status(404).entity("User: " + username + " not found!").build();
        }
    }

	/*@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response get() { (testListUsers.size() == 0) {
		testListUsers.add("cristhian");
		testListUsers.add("francisco");
		testListUsers.add("guillermo");
		testListUsers.add("juan");
		}
		testListUsers<String> result = testListUsers;
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
	public Response produceJSON(@PathParam("userID") String name) { (testListUsers.contains(name.toLowerCase()))
		{
			return Response.status(201).entity(new User_Old(name)).build(); 
		}
		else
		{
			return Response.status(404).entity("User: " + name + " not found!" ).build();
		} 
	}

	*/
}
