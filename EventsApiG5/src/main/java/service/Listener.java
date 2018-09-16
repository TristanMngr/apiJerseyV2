package service;

import java.net.URI;

import javax.servlet.ServletContextEvent;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class Listener implements javax.servlet.ServletContextListener {

	private static final String HTTP_API_KEY = "676397012:AAHtOXRAimIFfVy_C0Ut_US70Ls-tC5uBKI";
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		 System.out.println( "Good bye World!" );
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
        
               
		while(true)
		{
			System.out.println( "Hello World!" );
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}