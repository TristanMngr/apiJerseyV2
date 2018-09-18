package controller;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * This class launches the web application in an embedded Jetty container. This is the entry point to your application. The Java
 * command that is used for launching should fire this main method.
 */
public class Main {
	
    public static void main(String[] args) throws Exception {
    
    	ResourceConfig config = new ResourceConfig();
        config.packages("controller");
        config.property(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        config.register(JspMvcFeature.class);
        
        ServletContainer servletContainer = new ServletContainer(config);
        ServletHolder jerseyServlet = new ServletHolder(servletContainer);

        final WebAppContext root = new WebAppContext();
        
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        //context.addFilter(jerseyServlet, "/*",EnumSet.of(DispatcherType.REQUEST));
        //context.addFilter(null, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addServlet(jerseyServlet, "/*");
        server.start();
        server.join();
    }

}
