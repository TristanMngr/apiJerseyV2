package controller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.query.Query;
import dao.MongoDBConnection;
import model.User;
import service.ManagementService;

import javax.ws.rs.core.Response;

public class UsersControllerTest extends JerseyTest  {
	
	@Mock
	MongoDBConnection database;


	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		config.register(UsersController.class);
		config.register(LoginController.class);	
		
		ManagementService.createDAOs();
		return config;
	}

	@Test
	public void testGetIt() {
				
		Response response = target().path("users").request(MediaType.APPLICATION_JSON).get();
		System.out.println("++++++++++++++++" + response.toString() + "+++++++++++++++++++++++++++");
		assertEquals(200, response.getStatus());
	}
	
	private Response signUp() {

		JSONObject json = new JSONObject();
		json.put("username", "MyApp"); // MySecret
		System.out.println(json.toString());
		Response response = target().path("users").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlBcHA6TXlTZWNyZXQ=")
													.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		return response;
	}
		
	@Test
	public void testLoginOK() {
				
		long size = ManagementService.getUserDAO().count();
		
		Response response = signUp();
		assertEquals(201, response.getStatus());
		
		// Cree un usuario
		assertEquals(size+1, ManagementService.getUserDAO().count());
				
		JSONObject json = new JSONObject();
		json.put("username", "MyApp");
		
		// login usuario
		response  = target().path("login").request().header("Authorization", "Basic TXlBcHA6TXlTZWNyZXQ=")
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());
		
		Map<String, NewCookie> cookies = response.getCookies();
		NewCookie tokenCookie = cookies.get("tokenG5");
		NewCookie usernameCookie = cookies.get("username");
		
		assertEquals(usernameCookie.getValue(), "MyApp");
		response  = target().path("admin").request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();	
		
		assertEquals(200, response.getStatus());
		
		JSONObject answerFromResponse = new JSONObject(response.readEntity(String.class));
		
		assertEquals("admin", answerFromResponse.get("role").toString());
		
		
		response  = target().path("events/buscarEventos")
							.queryParam("codigo", "17920884849")
							.queryParam("nombre", "")
							.queryParam("categoryId", "")
							.queryParam("desde", "")
							.queryParam("hasta", "")
							.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();
		
		assertEquals(200, response.getStatus());
				
		response  = target().path("logout")
							.request()
							.cookie("username", usernameCookie.getValue())
							.cookie("tokenG5",tokenCookie.getValue())
							.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		assertEquals(200, response.getStatus());
		
		
		// Elimine un usuario
		Query<User> query = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyApp");
		ManagementService.getUserDAO().getDatastore().delete(query);
		
		assertEquals(size, ManagementService.getUserDAO().count());

	}
	

}
