package controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class EventsControllerTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		
		return config;
	}

	@Test
	public void testResponseFailure() {
				
		Response response = target().path("buscarEventos")
									.queryParam("codigo", "17920884849")
									.queryParam("nombre", "")
									.queryParam("categoryId", "")
									.queryParam("desde", "")
									.queryParam("hasta", "")	
									.request(MediaType.TEXT_PLAIN).get();
		assertEquals(404, response.getStatus());
	}
}
