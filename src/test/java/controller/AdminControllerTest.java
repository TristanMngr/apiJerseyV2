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
		
		long size = ManagementService.getUserDAO().count();

		// Crear un usuario
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("username", "MyUser"); // MySecret
		System.out.println(jsonUser.toString());
		Response response = target().path("users").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlVc2VyOk15U2VjcmV0")
													.post(Entity.entity(jsonUser.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(201, response.getStatus());
		assertEquals(size+1, ManagementService.getUserDAO().count());

		// login usuario
		response  = target().path("login").request().header("Authorization", "Basic TXlVc2VyOk15U2VjcmV0")
				.post(Entity.entity(jsonUser.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());
		
		Map<String, NewCookie> cookies = response.getCookies();
		NewCookie tokenCookie = cookies.get("tokenG5");
		NewCookie usernameCookie = cookies.get("username");
		
		// Creo una lista
				
		response  = target().path("eventsLists/create")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("nombreLista=listaTest1",MediaType.APPLICATION_FORM_URLENCODED),Response.class);	
	
		assertEquals(201, response.getStatus());
		//String answer = response.readEntity(String.class);
		JSONObject answer = new JSONObject(response.readEntity(String.class).toString());
		JSONObject jsonList = new JSONObject(answer.get("lista").toString());
		
		ObjectId objIdLista1 = new ObjectId(jsonList.get("hexId").toString());
		
		// Creo otra lista
		
		response  = target().path("eventsLists/create")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("nombreLista=listaTest2",MediaType.APPLICATION_FORM_URLENCODED),Response.class);	
	
		assertEquals(201, response.getStatus());
		//String answer = response.readEntity(String.class);
		answer = new JSONObject(response.readEntity(String.class).toString());
		jsonList = new JSONObject(answer.get("lista").toString());
		
		ObjectId objIdLista2 = new ObjectId(jsonList.get("hexId").toString());
		
		
		// Agrego un evento a Lista1
		
		response  = target().path("eventsLists/addEvent")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("codigo=52660070689&lista="+objIdLista1.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);	
		
		assertEquals(201, response.getStatus());
		
		// Agrego un evento a Lista2
		
		response  = target().path("eventsLists/addEvent")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("codigo=52660070689&lista="+objIdLista2.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);	
		
		assertEquals(201, response.getStatus());
		
		
		// Comparo Listados
		
		response  = target().path("admin/compare")
				.queryParam("user1", usernameCookie.getValue())
				.queryParam("list1", "listaTest1")
				.queryParam("user2", usernameCookie.getValue())
				.queryParam("list2", "listaTest2")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();

		assertEquals(200, response.getStatus());
		
		// Verifico cuantos usuarios tienen cargado ese elemento
		
		response  = target().path("admin/events/52660070689")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();
		
		assertEquals(200, response.getStatus());
		assertEquals("1", response.readEntity(String.class));
		
		// Verifico cuantas listas tiene el usuario
		
		response  = target().path("admin/users/MyUser")
				.request().cookie("username", usernameCookie.getValue()).cookie("tokenG5",tokenCookie.getValue()).get();

		answer = new JSONObject(response.readEntity(String.class).toString());
		
		assertEquals(200, response.getStatus());
		assertEquals(2, answer.get("cantListas"));
		assertEquals("MyUser", answer.get("username"));
		
		//logout
		JSONObject json = new JSONObject();
		json.put("username", "MyUser");
		
		response  = target().path("logout")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		assertEquals(200, response.getStatus());
				
		// Elimine un usuario
		Query<User> query = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyUser");
		ManagementService.getUserDAO().getDatastore().delete(query);
		
		assertEquals(size, ManagementService.getUserDAO().count());
		
		// Eliminar la Lista 1
		
		Query<EventsList> query1 = ManagementService.getEventsListDAO().getDatastore().find(EventsList.class, "id", new ObjectId(objIdLista1.toString()));
		ManagementService.getEventsListDAO().getDatastore().delete(query1);
		
		// Eliminar la Lista 2
		
		Query<EventsList> query2 = ManagementService.getEventsListDAO().getDatastore().find(EventsList.class, "id", new ObjectId(objIdLista2.toString()));
		ManagementService.getEventsListDAO().getDatastore().delete(query2);
		
	}
}
