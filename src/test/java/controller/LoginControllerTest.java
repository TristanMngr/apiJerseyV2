package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;

public class LoginControllerTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(LoginController.class);
	}

	@Test
	public void testLogin() {
		
		JSONObject json = new JSONObject();
		json.put("username", "test");
		System.out.println(json.toString());
		Response response = target().path("login").request(MediaType.TEXT_PLAIN).header("Authorization", "Basic TXlBcHA6TXlTZWNyZXQ=")
													.post(Entity.entity(json.toString(),MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		assertEquals(401, response.getStatus());
	}
	
}
