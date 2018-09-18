package controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;


@Path("/")
public class MainController {
     
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() {
		Map<String, String> model = new HashMap<String, String>();
        model.put("hello", "Hello");
        model.put("world", "World! I'm index.jsp");
        return new Viewable("/index", model);
    }
 
}
