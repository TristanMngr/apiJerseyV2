package controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.NewAlarm;
import model.User;
import org.json.JSONObject;
import service.AlarmService;
import service.EventbriteService;
import service.ManagementService;
import service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/alarms")
public class AlarmController{

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearLista(@FormParam("name") String name, @FormParam("categoryId") String categoryId, @Context ContainerRequestContext crc) throws JsonProcessingException {
        User user = UserService.currentUser(crc);

        return Response.status(201).entity(AlarmService.createAlarm(user, name, categoryId)).build();
    }

    @Path("/destroy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@FormParam("name") String name, @Context ContainerRequestContext crc) throws JsonProcessingException {
        User user = UserService.currentUser(crc);

        return Response.status(200).entity(AlarmService.removeAlarm(user, name)).build();
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlarmByName(@PathParam("name") String name, @Context ContainerRequestContext crc) throws JsonProcessingException {
        User user = UserService.currentUser(crc);

        return Response.status(200).entity(AlarmService.getAlarmByName(user, name)).build();
    }

    @Path("/reloadEvents")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearLista(@Context ContainerRequestContext crc) throws IOException {
        User user = UserService.currentUser(crc);

        EventbriteService.getEventsSinceLastConnexion(user);

        return Response.ok(new JSONObject("{\"error\":\"0\"}"), MediaType.APPLICATION_JSON).build();
    }
    
    
    @Path("/createAlarm")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Response createAlarm(@CookieParam("username") String username, String data) {
    	System.out.println(this.getClass().getName() + ":: createAlarm ...");
    	    	
    	JSONObject json = new JSONObject(data);    	
    	NewAlarm alarm = new NewAlarm(	json.get("name").toString(), 
    									json.get("codigo").toString(), 
    									json.get("nombre").toString(), 
    									json.get("categoryId").toString(), 
    									null, null);
    	
    	ManagementService.getUserDAO().saveAlarm(username, alarm);
    	
        return Response.ok().build();
    }


}
