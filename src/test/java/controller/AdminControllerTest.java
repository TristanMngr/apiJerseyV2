package controller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.query.Query;

import dao.MongoDBConnection;
import model.EventsList;
import model.User;
import service.ManagementService;

public class AdminControllerTest extends JerseyTest {
	@Mock
	MongoDBConnection database;


	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		return config;
	}

	@Test
	public void testReqadminwoLogin() {
		ManagementService.createDAOs();
		
		JSONObject json = new JSONObject();
		json.put("username", "admin");
		Response response  = target().path("admin").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic YWRtaW46MTIzNDU2YUE=")
													.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(405, response.getStatus());
	}
	
	
	@Test
	public void testAdminLogin() {
		ManagementService.createDAOs();
		
		JSONObject json = new JSONObject();
		json.put("username", "admin");
		Response response  = target().path("login").request().header("Authorization", "Basic YWRtaW46MTIzNDU2YUE=")
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());
			
		Map<String, NewCookie> cookies = response.getCookies();
		NewCookie tokenAdminCookie = cookies.get("tokenG5");
		NewCookie usernameAdminCookie = cookies.get("username");
		
		assertEquals(usernameAdminCookie.getValue(), "admin");
		
		response  = target().path("admin").request().cookie("username", usernameAdminCookie.getValue()).cookie("tokenG5",tokenAdminCookie.getValue()).get();

		assertEquals(200, response.getStatus());
		
		long size = ManagementService.getUserDAO().count();

		// Crear un usuario
		JSONObject jsonUser = new JSONObject();
		json.put("username", "MyUser"); // MySecret
		System.out.println(jsonUser.toString());
		response = target().path("users").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlVc2VyOk15U2VjcmV0")
													.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(201, response.getStatus());
		assertEquals(size+1, ManagementService.getUserDAO().count());

		// login usuario
		response  = target().path("login").request().header("Authorization", "Basic TXlVc2VyOk15U2VjcmV0")
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());
		
		cookies = response.getCookies();
		NewCookie tokenCookie = cookies.get("tokenG5");
		NewCookie usernameCookie = cookies.get("username");
		
		// Creo una lista
				
		response  = target().path("eventsLists/create")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("nombreLista=listaTest",MediaType.APPLICATION_FORM_URLENCODED),Response.class);	
	
		assertEquals(201, response.getStatus());
		//String answer = response.readEntity(String.class);
		JSONObject answer = new JSONObject(response.readEntity(String.class).toString());
		JSONObject jsonList = new JSONObject(answer.get("lista").toString());
		
		ObjectId objId = new ObjectId(jsonList.get("hexId").toString());
		
		// Elimine un usuario
		Query<User> query = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyUser");
		ManagementService.getUserDAO().getDatastore().delete(query);
		
		assertEquals(size, ManagementService.getUserDAO().count());
		
		// TODO: Eliminar la lista no funciona.
		
		// Eliminar la Lista
		
		Query<EventsList> query1 = ManagementService.getEventsListDAO().getDatastore().find(EventsList.class, "_id", objId.toString());
		ManagementService.getUserDAO().getDatastore().delete(query1);
		
	}
}
