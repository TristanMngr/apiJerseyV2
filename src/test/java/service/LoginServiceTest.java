package service;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class LoginServiceTest  extends JerseyTest {
	@Override
	protected Application configure() {
		return new ResourceConfig(LoginService.class);
	}

	
	@Test
	public void testVariableAvailable() {
		String myToken = LoginService.getToken();
		assertEquals(10, myToken.length());
	}
}
