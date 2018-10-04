package controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;


@Path("/")
public class MainController {
     
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
		Map<String, String> model = new HashMap<String, String>();
        model.put("hello", "Hello");
        model.put("world", "World! I'm index.jsp");
        return Response.ok(new Viewable("/jsp/index", model)).build();
    }
 
}
