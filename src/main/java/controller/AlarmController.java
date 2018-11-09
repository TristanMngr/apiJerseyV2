package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.UserDAO;
import model.User;
import org.bson.types.ObjectId;
import service.EventbriteService;
import service.ManagementService;
import service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/alarms")
public class AlarmController {

    // TODO
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response index() throws JsonProcessingException {
        return Response.ok().entity(EventbriteService.getEventsSinceLastConnexion(ManagementService.getUserDAO().getByUserId(new ObjectId("5be5f0ad43b244784d05a68f")))).build();
    }
}
