package service;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
 
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
 
import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.mvc.Viewable;

 
/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
     
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
                                                        .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
                                                        .entity("Access blocked for all users !!").build();
      
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        System.out.println("I'm on filter");
    	
    	Method method = resourceInfo.getResourceMethod();
    	    	
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
            
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            
            final List<String> session = headers.get("Cookie");
            
           if(session != null && !session.isEmpty())
           {
        	   // TODO: Validate existing session
        	   System.out.println(this.getClass().getName() + ":: session is not null and not empty");
        	   return;
           }
        	   
        	if(method.getName().equals("index") && ( session == null || session.isEmpty()))
        	{
        		requestContext.abortWith(Response.ok(new Viewable("/jsp/logon", null)).build());
        		return;
        	}           

            //If no authorization information present; block access
            if(method.getName().equals("login") && (authorization == null || authorization.isEmpty()))
            {
            	System.out.println(this.getClass().getName() + ":: authorization is null or Empty");
            	requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            
            if(method.getName().equals("index"))
    		{
            	// TODO: Guardar Usuario / Token en un DAO
            	
            	//Get encoded username and password
                final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
                  
                //Decode username and password
                String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
      
                //Split username and password tokens
                final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                final String username = tokenizer.nextToken();
                final String password = tokenizer.nextToken();
                  
                //Verifying Username and password
                System.out.println(this.getClass().getName() + ":: username = " + username);
                System.out.println(this.getClass().getName() + ":: password = " + password);
                  
                //Verify user access
                if(method.isAnnotationPresent(RolesAllowed.class))
                {
                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                      
                    //Is user valid?
                    if( ! LoginService.isUserAllowed(username, password, rolesSet))
                    {
                        requestContext.abortWith(ACCESS_DENIED);
                        return;
                    }
                }
    	
    		}
            
        }
    }

}