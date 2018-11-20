package service;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class EncryptionServicesTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		return config;
	}
	
	@Test
	public void testEncrypt() {
		assertEquals("o7kXsF19UsvGFZGQthYBAQ==", EncryptionServices.encrypt("EVENTBRITE"));
	}
	
	@Test
	public void testDecrypt() {
		assertEquals(EncryptionServices.decrypt("o7kXsF19UsvGFZGQthYBAQ=="), "EVENTBRITE");
	}

}
