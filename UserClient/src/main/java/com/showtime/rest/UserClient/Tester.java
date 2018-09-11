package com.showtime.rest.UserClient;

import java.net.URI;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import main.java.com.showtime.rest.User;


public class Tester {
	  public static void main(String[] args) {


	      ClientConfig config = new ClientConfig();
	      Client client = ClientBuilder.newClient(config);
	      client.register(new LoggingFilter());
	      WebTarget service = client.target(getBaseURI());

	      User user; 
	      
	      try {
	    	  //Response response = service.path("rest").path("users").path("put").request().put(Entity.entity(user,MediaType.APPLICATION_JSON),Response.class);
	    	  user = new User("enzo");
	    	  Response response = service.path("rest").path("users").request().put(Entity.entity(user,MediaType.APPLICATION_JSON),Response.class);
	    	  System.out.println(response.getStatus());
	    	  user = new User("ariel");
	    	  Entity<User> json = Entity.entity(user,MediaType.APPLICATION_JSON);
	    	  response = service.path("rest").path("users").request().post(Entity.entity(user,MediaType.APPLICATION_JSON),Response.class);

	    	  
	        }
	        catch(ProcessingException exception)
	        {
	        	System.out.println( "Explote!" );
	        	System.out.println(exception.getMessage());
	        	exception.printStackTrace();
	        }
	      
	  }

	  private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://localhost:8080/apiJerseyV2/").build();
	  }
	}