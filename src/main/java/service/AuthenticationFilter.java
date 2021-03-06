package service;


import java.lang.reflect.Method;
import java.util.List;
 
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;
 
import org.glassfish.jersey.server.mvc.Viewable;

 
/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
    
	private static boolean allowAnonymousUser = false;
	
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final ResponseBuilder ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
                                                        .entity("You cannot access this resource");
    private static final ResponseBuilder ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
                                                        .entity("Access blocked for all users !!");
      
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
    	
    	if(allowAnonymousUser) {
    		System.out.println(this.getClass().getName() + ":: allowAnonymousUser");
    		return;
    	}
    		
    	
    	
    	System.out.println(this.getClass().getName() + ":: filtering ...");
    	System.out.println(this.getClass().getName() + ":: " + requestContext.getMethod().toString() + 
    														" " + requestContext.getUriInfo().getRequestUri());
    	Method method = resourceInfo.getResourceMethod();
    	    	
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_FORBIDDEN.build());
                return;
            }
            
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            
            //If no authorization information present; block access
            if(method.getName().equals("login") && (authorization == null || authorization.isEmpty()))
            {
            	System.out.println(this.getClass().getName() + ":: authorization is null or Empty");
            	requestContext.abortWith(ACCESS_DENIED.build());
                return;
            }
            
            // Validate Session
        	
        	if(!SessionService.validateSession(requestContext.getCookies())){
       		
        		System.out.println(this.getClass().getName() + ":: Session couldn't be validated");
        		
        		Viewable view = new Viewable("/jsp/logon");
        		requestContext.abortWith(Response.ok(view).build());
        		
                return;
        	}
        	
        	if(method.isAnnotationPresent(RolesAllowed.class)) {
        		RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
                System.out.println("rolled allowed");
                System.out.println(rolesAllowed);
        		String roleAllowed = rolesAllowed.value()[0];

        		if(!UserService.validateRole(requestContext.getCookies(), roleAllowed))
        		{
        			System.out.println(this.getClass().getName() + ":: User has not the necessary roles");
            		
        			requestContext.abortWith(ACCESS_DENIED.build());
                    return;
        		}
        	}
        	            
        }
    }

}