package controller;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import service.JSONClass;

// http://localhost:8080/api/rest/otherJSON
// Probar en Chrome

@Path("/otherJSON")
public class JSONGetter {
	 @GET
	 @Produces({MediaType.APPLICATION_JSON})
	 public JSONClass get() {
		 return new JSONClass("Cristhian", 1, Arrays.asList("Elemento1", "Elemento2", "Elemento3"));
	 }
	 
}
