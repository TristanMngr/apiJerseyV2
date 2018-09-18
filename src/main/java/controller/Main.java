package controller;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
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
//
//
//public class JettyFilter {
//
//	  public static void main(final String[] args) throws Exception {
//	    Server server = new Server(8080);
//
//	    ServletHandler handler = new ServletHandler();
//	    server.setHandler(handler);
//
//	    handler.addServletWithMapping(HelloServlet.class, "/*");
//	    handler.addFilterWithMapping(HelloPrintingFilter.class, "/*",
//	        EnumSet.of(DispatcherType.REQUEST));
//
//	    server.start();
//	    server.join();
//	  }
//
//	  public static class HelloServlet extends HttpServlet {
//	    private static final long serialVersionUID = 1L;
//
//	    @Override
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//	        throws ServletException, IOException {
//	      response.setContentType("text/html");
//	      response.setStatus(HttpServletResponse.SC_OK);
//	      response.getWriter().println("<h1>Hello SimpleServlet</h1>");
//	    }
//	  }
//
//	  public static class HelloPrintingFilter implements Filter {
//	    @Override
//	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//	        throws IOException, ServletException {
//	      System.out.print("hello from filter");
//	    }
//
//	    @Override
//	    public void init(FilterConfig arg0) throws ServletException {
//
//	    }
//
//	    @Override
//	    public void destroy() {}
//	  }
//	}