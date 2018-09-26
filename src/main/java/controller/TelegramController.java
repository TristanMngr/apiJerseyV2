package controller;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.logging.BotLogger;

@Path("/telegram")
public class TelegramController {
	
	private final ConcurrentHashMap<String, WebhookBot> callbacks = new ConcurrentHashMap<>();
	
	// TODO: Archivo configuracion o Variable Entorno
	
	private static final String HTTP_API_PART_1 = "676397012";
	private static final String HTTP_API_PART_2 = "AAHtOXRAimIFfVy_C0Ut_US70Ls-tC5uBKI";
	
	 @OPTIONS
	 public Response getOptions() {
		 System.out.println("getOptions");
		 return Response.ok().header("Access-Control-Allow-Methods", "POST, GET, OPTIONS").build();
	 }
	
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
    @Path("/{botPath}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
        System.out.println("***** Receive updated ***** ");
    	if (callbacks.containsKey(botPath)) {
            try {
                BotApiMethod<?> response = callbacks.get(botPath).onWebhookUpdateReceived(update);
                if (response != null) {
                    response.validate();
                }
                return Response.ok(response).build();
            } catch (TelegramApiValidationException e) {
                BotLogger.severe("RESTAPI", e);
                return Response.serverError().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
	
//	@Path("callback")
//	public class RestApi {
//
//	    private final ConcurrentHashMap<String, WebhookBot> callbacks = new ConcurrentHashMap<>();
//
//	    public RestApi() {
//	    }
//
//	    public void registerCallback(WebhookBot callback) {
//	        if (!callbacks.containsKey(callback.getBotPath())) {
//	            callbacks.put(callback.getBotPath(), callback);
//	        }
//	    }
//
//	    @POST
//	    @Path("/{botPath}")
//	    @Consumes(MediaType.APPLICATION_JSON)
//	    @Produces(MediaType.APPLICATION_JSON)
//	    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
//	        System.out.println("***** Receive updated ***** ");
//	    	if (callbacks.containsKey(botPath)) {
//	            try {
//	                BotApiMethod<?> response = callbacks.get(botPath).onWebhookUpdateReceived(update);
//	                if (response != null) {
//	                    response.validate();
//	                }
//	                return Response.ok(response).build();
//	            } catch (TelegramApiValidationException e) {
//	                BotLogger.severe("RESTAPI", e);
//	                return Response.serverError().build();
//	            }
//	        }
//
//	        return Response.status(Response.Status.NOT_FOUND).build();
//	    }
//
//	    @GET
//	    @Path("/{botPath}")
//	    @Produces(MediaType.APPLICATION_JSON)
//	    public String testReceived(@PathParam("botPath") String botPath) {
//	        if (callbacks.containsKey(botPath)) {
//	            return "Hi there " + botPath + "!";
//	        } else {
//	            return "Callback not found for " + botPath;
//	        }
//	    }
//	}
	
	


}
