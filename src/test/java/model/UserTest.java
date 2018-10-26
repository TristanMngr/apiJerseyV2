package model;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class UserTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new ResourceConfig(User.class);
	}

	
	@Test
	public void testVariableAvailable() {
		User userOne = new User("Cristhian", "TXlBcHA6TXlTZWNyZXQ=");
		assertEquals("Cristhian", userOne.getUserName());
	}
}
