package service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Cookie;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class SessionServiceTest extends JerseyTest {
	@Override
	protected Application configure() {
		ResourceConfig config = new ResourceConfig();
		config.packages("controller");
		return config;
	}

	
	@Test
	public void testVariableAvailable() {
		Map<String, Cookie> cookies = new HashMap<String, Cookie> ();;
		cookies.put("tokenG5", new Cookie("tokenG5", "v9wiWZpUq8"));
		
		String tokenFromCookie = "";
		
		for (Cookie c : cookies.values()) 
        {
            if (c.getName().equals("tokenG5")) {
            	tokenFromCookie = c.getValue();
            }
        }
		
		assertEquals("v9wiWZpUq8", tokenFromCookie);
	}
}
