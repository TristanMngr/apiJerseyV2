package controller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dao.MongoDBConnection;
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
		NewCookie token = cookies.get("tokenG5");
		
		response  = target().path("admin").request().cookie("username", "admin").cookie("tokenG5",token.getValue()).get();

		assertEquals(200, response.getStatus());
	}
}
