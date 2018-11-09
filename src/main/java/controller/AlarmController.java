package controller;

import org.bson.types.ObjectId;
import service.EventbriteService;
import service.ManagementService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/alarms")
public class AlarmController {

    // TODO
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response index() throws IOException {
        return Response.ok(EventbriteService.getEventsSinceLastConnexion(ManagementService.getUserDAO().getByUserId(new ObjectId("5be5f0ad43b244784d05a68f"))), MediaType.APPLICATION_JSON).build();
    }
}
