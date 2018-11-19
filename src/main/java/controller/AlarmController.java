package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import model.User;
import org.bson.types.ObjectId;
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

import static service.EventbriteService.getEventsSinceLastConnexion;

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

        getEventsSinceLastConnexion(user);

        return Response.ok(new JSONObject("{\"error\":\"0\"}"), MediaType.APPLICATION_JSON).build();
    }


}
