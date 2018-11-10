package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.User;
import org.bson.types.ObjectId;
import service.AlarmService;
import service.EventbriteService;
import service.ManagementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/alarms")
public class AlarmController {

    // TODO to remove
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response index() throws IOException {
        return Response.ok(EventbriteService.getEventsSinceLastConnexion(ManagementService.getUserDAO().getByUserId(new ObjectId("5be5f0ad43b244784d05a68f"))), MediaType.APPLICATION_JSON).build();
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearLista(@FormParam("name") String name, @FormParam("categoryId") String categoryId) throws JsonProcessingException {
        // TODO create method to get connected user
        User                user        = ManagementService.getUserDAO().getByUserId(new ObjectId("5be5f0ad43b244784d05a68f"));

        return Response.status(201).entity(AlarmService.createAlarm(user, name, categoryId)).build();
    }

    @Path("/destroy")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@FormParam("name") String name) throws JsonProcessingException {
        // TODO create method to get connected user
        User                user        = ManagementService.getUserDAO().getByUserId(new ObjectId("5be5f0ad43b244784d05a68f"));

        return Response.status(200).entity(AlarmService.removeAlarm(user, name)).build();
    }
}
