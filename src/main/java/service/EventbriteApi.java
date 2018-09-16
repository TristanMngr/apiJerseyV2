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

public class EventbriteApi {
	  public static String getEvent(String eventID) {


	      ClientConfig config = new ClientConfig();
	      Client client = ClientBuilder.newClient(config);
	      client.register(new LoggingFilter());
	      WebTarget service = client.target(getBaseURI());
	      
	      String output = "";
	    
	      try {
	    	  Response response = service.path(eventID).queryParam("token",getAppKey()).request(MediaType.APPLICATION_JSON).get();	
	    	  output = response.readEntity(String.class);
	    	  
	        }
	        catch(ProcessingException exception)
	        {
	        	System.out.println( "Explote!" );
	        	System.out.println(exception.getMessage());
	        	exception.printStackTrace();
	        }
		return output;
	      
	  }

	  private static URI getBaseURI() {
	    return UriBuilder.fromUri("https://www.eventbriteapi.com/v3/events/").build();
	  }
	  
	  private static String getAppKey() {
		    return "Q2U3MHJELN4VOBCARDUQ";
	  }
}
