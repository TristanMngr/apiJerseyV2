package service;

import java.net.URI;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class TelegramInterface {
	
	private static final String HTTP_API_KEY = "676397012:AAHtOXRAimIFfVy_C0Ut_US70Ls-tC5uBKI";
	
	// TODO: Not yet implemented
	public static String searchEvent(String criterio) {
		
		// Do something with criterio
		return "<evento>";
	}
	
	public static void addEvent(String event, String username) {
		
		// Do something with event
		return;
	}
	
	public static void main(String[] args) throws Exception
	{
	    System.out.println("Telegram Interface has started");  
		
		ClientConfig config = new ClientConfig();
	      Client client = ClientBuilder.newClient(config);
	      client.register(new LoggingFilter());
	      WebTarget service = client.target(getBaseURI());
	      
	      
	      
	      try {
	    	  Response response = service.path("getUpdates").request(MediaType.APPLICATION_JSON).get();	
	    	  String output = response.readEntity(String.class);
	    	  System.out.println("**** " + output + "****");
	    	  
	        }
	        catch(ProcessingException exception)
	        {
	        	System.out.println( "Explote!" );
	        	System.out.println(exception.getMessage());
	        	exception.printStackTrace();
	        }
	      
	}
	
	private static URI getBaseURI() {
	    return UriBuilder.fromUri("https://api.telegram.org/bot" + HTTP_API_KEY + "/").build();
	}
}
