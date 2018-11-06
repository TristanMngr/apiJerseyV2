package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;

import dao.MongoDBConnection;
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
		
		
		ManagementService.createDAOs();
		return config;
	}

	@Test
	public void testGetIt() {

		Response response = target().path("users").request(MediaType.APPLICATION_JSON).get();
		System.out.println("+" + response.toString() + "+");
		assertEquals(200, response.getStatus());
	}

}
