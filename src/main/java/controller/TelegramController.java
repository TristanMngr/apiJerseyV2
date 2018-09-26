package controller;

import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Path("/telegram")
public class TelegramController {
	
	// TODO: Archivo configuracion o Variable Entorno
	
	private static final String HTTP_API_PART_1 = "676397012";
	private static final String HTTP_API_PART_2 = "AAHtOXRAimIFfVy_C0Ut_US70Ls-tC5uBKI";
	
    // TODO: Remove method once WebHook is correctly set up.
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response checkUpdates() 
	{
		System.out.println("Telegram Interface has started");  
		
		ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    client.register(new LoggingFilter());
	    WebTarget service = client.target(getBaseURI());
	      
	    try {
	    	Response response = service.path("getUpdates").request(MediaType.APPLICATION_JSON).get();	
	    	String output = response.readEntity(String.class);
	    	
	    	return Response.status(201).entity(output).build();
	    }
	    catch(ProcessingException exception)
	    {
	    	System.out.println( "Explote!" );
	        System.out.println(exception.getMessage());
	        exception.printStackTrace();
	    }
		
	    return null;
	      
	}
	
	private static URI getBaseURI() {
	    return UriBuilder.fromUri("https://api.telegram.org/bot" + HTTP_API_PART_1 + ":" + HTTP_API_PART_2 + "/").build();
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReceived(Update update) {
        System.out.println("***** Receive updated ***** ");
        // TODO: Do something with the update received.
        System.out.println(update);
        return Response.ok().build();
    }

}
