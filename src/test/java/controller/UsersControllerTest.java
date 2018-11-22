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

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import dao.MongoDBConnection;
import model.EventsList;
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

	@Test
	public void testLoginOK() {
		ManagementService.createDAOs();

		long size = ManagementService.getUserDAO().count();

		// Crear un usuario
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("username", "MyUser"); // MySecret

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
		
		// getAllCategories
		
		response  = target().path("events/categories")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.get();	
		
		assertEquals(200, response.getStatus());

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


		// Creo una lista con el mismo nombre

		response  = target().path("eventsLists/create")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity("nombreLista=listaTest1",MediaType.APPLICATION_FORM_URLENCODED),Response.class);	

		assertEquals(204, response.getStatus());		

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

		//logout
		JSONObject json = new JSONObject();
		json.put("username", "MyUser");

		response  = target().path("logout")
				.request()
				.cookie("username", usernameCookie.getValue())
				.cookie("tokenG5",tokenCookie.getValue())
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		assertEquals(200, response.getStatus());




		signUpAdminUser();

		// Agregar ADMIN Role

		Datastore ds = ManagementService.getUserDAO().getDatastore();

		Query<User> query = ds.find(User.class, "userName", "MyAdmin");
		UpdateOperations<User> updateOps = ds.createUpdateOperations(User.class).set("role", "ADMIN");
		ds.update(query, updateOps);

		// login ADmin usuario

		JSONObject jsonAdminUser = new JSONObject();
		jsonUser.put("username", "MyAdmin"); // MySecret

		response  = target().path("login").request().header("Authorization", "Basic TXlBZG1pbjpNeVNlY3JldA==")
				.post(Entity.entity(jsonUser.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		assertEquals(200, response.getStatus());

		cookies = response.getCookies();
		NewCookie tokenAdminCookie = cookies.get("tokenG5");
		NewCookie usernameAdminCookie = cookies.get("username");

		// Verifico cuantos usuarios tienen cargado ese elemento		

		response  = target().path("admin/events/52660070689")
				.request().cookie("username", usernameAdminCookie.getValue()).cookie("tokenG5",tokenAdminCookie.getValue()).get();

		assertEquals(200, response.getStatus());
		assertEquals("1", response.readEntity(String.class));


		// Comparo Listados

		response  = target().path("admin/compare")
				.queryParam("user1", usernameCookie.getValue())
				.queryParam("list1", "listaTest1")
				.queryParam("user2", usernameCookie.getValue())
				.queryParam("list2", "listaTest2")
				.request().cookie("username", usernameAdminCookie.getValue()).cookie("tokenG5",tokenAdminCookie.getValue()).get();

		assertEquals(200, response.getStatus());

		// Verifico cuantas listas tiene el usuario

		response  = target().path("admin/users/MyUser")
				.request().cookie("username", usernameAdminCookie.getValue()).cookie("tokenG5",tokenAdminCookie.getValue()).get();

		answer = new JSONObject(response.readEntity(String.class).toString());

		assertEquals(200, response.getStatus());
		assertEquals(2, answer.get("cantListas"));
		assertEquals("MyUser", answer.get("username"));

		// Verifico cuantas listas tiene el usuario

		response  = target().path("admin/users/MyUser/events")
				.request().cookie("username", usernameAdminCookie.getValue()).cookie("tokenG5",tokenAdminCookie.getValue()).get();
		
		JSONArray array = new JSONArray(response.readEntity(String.class).toString());
		
		assertEquals(200, response.getStatus());
		assertEquals(2, array.length());
		
		cleanup(size, objIdLista1, objIdLista2);

	}

	void cleanup(long size, ObjectId objIdLista1,ObjectId objIdLista2) {
		// Elimine un usuario
		Query<User> query = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyUser");
		ManagementService.getUserDAO().getDatastore().delete(query);

		assertEquals(size+1, ManagementService.getUserDAO().count());

		Query<User> query1 = ManagementService.getUserDAO().getDatastore().find(User.class, "userName", "MyAdmin");
		ManagementService.getUserDAO().getDatastore().delete(query1);

		assertEquals(size, ManagementService.getUserDAO().count());


		// Eliminar la Lista 1

		Query<EventsList> query2 = ManagementService.getEventsListDAO().getDatastore().find(EventsList.class, "id", new ObjectId(objIdLista1.toString()));
		ManagementService.getEventsListDAO().getDatastore().delete(query2);

		// Eliminar la Lista 2

		Query<EventsList> query3 = ManagementService.getEventsListDAO().getDatastore().find(EventsList.class, "id", new ObjectId(objIdLista2.toString()));
		ManagementService.getEventsListDAO().getDatastore().delete(query3);
	}

	private Response signUpAdminUser() {

		JSONObject json = new JSONObject();
		json.put("username", "MyAdmin"); // MySecret
		System.out.println(json.toString());
		Response response = target().path("users").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlBZG1pbjpNeVNlY3JldA==")
				.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		return response;
	}


}
