package controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import dao.MongoDBConnection;
import model.User;
import service.AuthenticationFilter;
import service.ManagementService;
import service.SessionService;

import javax.ws.rs.core.Response;

public class UsersControllerTest extends JerseyTest  {
	
	@Mock
	MongoDBConnection database;


	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		config.register(AuthenticationFilter.class);
		
		ManagementService.createDAOs();
		return config;
	}

	@Test
	public void testGetIt() {
				
		Response response = target().path("users").request(MediaType.APPLICATION_JSON).get();
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
		
		// Agregar ADMIN Role
		
		Datastore ds = ManagementService.getUserDAO().getDatastore();
		
		Query<User> query = ds.find(User.class, "userName", "MyApp");
		UpdateOperations<User> updateOps = ds.createUpdateOperations(User.class).set("role", "ADMIN");
		ds.update(query, updateOps);
				
		JSONObject json = new JSONObject();
		json.put("username", "MyApp");
		
		// login usuario
		response  = target().path("login").request().header("Authorization", "Basic TXlBcHA6TXlTZWNyZXQ=")
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());
				
		Map<String, NewCookie> cookies = response.getCookies();
		NewCookie tokenCookie = cookies.get("tokenG5");
		NewCookie usernameCookie = cookies.get("username");
		
		Map<String, Cookie> normalCookies = new HashMap<String, Cookie>();
		
		for(String key : cookies.keySet())
		{
			normalCookies.put(key, cookies.get(key).toCookie());
		}
		
		assertEquals(true,SessionService.validateSession(normalCookies));
		
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
		
		response  = target().path("admin/events/search")
				.queryParam("since", "-1")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();

		assertEquals(200, response.getStatus());

		
		response  = target().path("admin/users")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();

		assertEquals(200, response.getStatus());

		response  = target().path("admin/users/administrador_x")
				.queryParam("since", "-1")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();

		assertEquals(204, response.getStatus());
				
		response  = target().path("logout")
							.request()
							.cookie("username", usernameCookie.getValue())
							.cookie("tokenG5",tokenCookie.getValue())
							.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		assertEquals(200, response.getStatus());
		
		
		// Elimine un usuario
		Query<User> query2 = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyApp");
		ManagementService.getUserDAO().getDatastore().delete(query2);
		
		assertEquals(size, ManagementService.getUserDAO().count());

	}
	

}
