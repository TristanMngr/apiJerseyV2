package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

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
		System.out.println("+" + response.toString() + "+");
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
	public void testSignUp() {
		
		long size = ManagementService.getUserDAO().count();
		
		Response response = signUp();
		assertEquals(201, response.getStatus());
		
		assertEquals(size+1, ManagementService.getUserDAO().count());
		
		Query<User> query = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyApp");
		ManagementService.getUserDAO().getDatastore().delete(query);
		
		assertEquals(size, ManagementService.getUserDAO().count());
		
		return;
	}
	
	//@Test
	public void testLoginOK() {
		
		//Response response = signUp();
		//assertEquals(201, response.getStatus());
		
		System.out.println(ManagementService.getUserDAO().count());
		
		JSONObject json = new JSONObject();
		json.put("username", "MyApp");
		System.out.println(json.toString());
		Response response  = target().path("login").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlBcHA6TXlTZWNyZXQ=")
													.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		System.out.println(response.toString());
		assertEquals(401, response.getStatus());
	}
	

}
