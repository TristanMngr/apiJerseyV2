package service;

import static org.junit.Assert.assertNotEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class EventbriteApiTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(EventbriteService.class);
	}

	
	@Test
	public void testVariableAvailable() {
		String myKey = System.getenv("EVENTBRITE_KEY");
		assertNotEquals(null, myKey);
		assertNotEquals("", myKey);
	}
	
	@Test
	public void testgetAllCategories() {
		String allCategories = EventbriteService.getAllCategories();
		System.out.println(allCategories);
		assertNotEquals(allCategories, "");
	}
}
