package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class UsersControllerTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(UsersController.class);
	}

	@Test
	public void testGetIt() {
		Response response = target().path("users").request(MediaType.APPLICATION_JSON).get();
		System.out.println(response.toString());
		assertEquals(201, response.getStatus());
	}

}
