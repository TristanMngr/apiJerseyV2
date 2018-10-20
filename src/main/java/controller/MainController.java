package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;
import service.ManagementService;

@Path("/")
public class MainController {


    public MainController() {
        /*I move the creation DAO in a filter (run when the app is launch), because we can't access them if we don't pass by this route*/
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() {
        return new Viewable("/jsp/index");
    }

}
