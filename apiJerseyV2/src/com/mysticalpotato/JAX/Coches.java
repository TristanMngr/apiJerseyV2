package com.mysticalpotato.JAX;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mysticalpotato.data.Coche;

@Path("/coches")
public class Coches {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Coche getIt() {
		Coche c = new Coche();
		c.setMarca("Ferrari");
		c.setModelo("F45");
		c.setPotencia(430);
		return c;
	}
	
	 @Path("/post")
	 @PUT
	 @Consumes(MediaType.APPLICATION_XML)
	 public Response createUserInJSON(Coche coche) {
		 System.out.println("Hice un POST");
		String result = "Car saved : " + coche;
		return Response.status(201).entity(result).build();		
	}
	 
	 @Path("/otherHTML")
 	@GET
 	@Produces("text/html")
 	public Response getStartingPage()
 	{
 		String output = "<h1>Hello World!<h1>" +
 				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
 		return Response.status(200).entity(output).build();
 	}
}