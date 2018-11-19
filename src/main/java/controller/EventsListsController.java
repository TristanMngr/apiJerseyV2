package controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.User;

import java.io.IOException;

import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import service.EventsListsService;
import service.LoginService;
import service.ManagementService;
import service.UserService;

@Path("/eventsLists")
public class EventsListsController {

    private String loggedUser="5bcbba1743b244dd134d6f45"; //TODO getUserLogged()
    
    private void setUserLogged(String username) {	
    	User user = ManagementService.getUserDAO().getUserByName(username);
    	loggedUser = user.getId().toString();    	
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLists() throws JsonProcessingException {
        return Response.status(201).entity(EventsListsService.getAllLists()).build();
    }

    @Path("/getFromUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListsFromUser(@Context UriInfo uriDetails, @Context ContainerRequestContext crc) throws JsonProcessingException {
        User user = UserService.currentUser(crc);
        return Response.ok(user).build();
    }

    /**
     * @param params
     * @return imprime en pantalla el json de lassss listassss de eventos
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    //public Response crearLista(String params) throws JsonProcessingException {
    public Response crearLista(@Context HttpHeaders httpHeaders, String params) throws JsonProcessingException {
        //TODO: verificar que no estamos creando una lista con el mismo nombre para el mismo usuario.
    	System.out.println(this.getClass().getName() + ":: crearLista ...");
    	String username = LoginService.getUserFromCookie(httpHeaders.getCookies());	
        setUserLogged(username);
    	
        System.out.println(this.getClass().getName() + ":: username = " + username);
        System.out.println(this.getClass().getName() + ":: params = " + params);
        
        
    	Map<String, String> parametros = ManagementService.getPostParams(params);
    	
        String nombreLista = parametros.get("nombreLista");
        System.out.println(this.getClass().getName() + ":: nombreLista = " + nombreLista);
        
        nombreLista=nombreLista.replace("+", " ");
        
        System.out.println(this.getClass().getName() + ":: nombreLista = " + nombreLista);
        
        String userId = this.loggedUser;
        return Response.status(201).entity(EventsListsService.create(nombreLista, userId)).build();
    }

    @Path("/addEvent")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvent(String params) throws IOException {
    	System.out.println(this.getClass().getName() + ":: addEvent ...");
    	Map<String, String> parametros = ManagementService.getPostParams(params);
    	
    	Long codigoEvento = Long.parseLong(parametros.get("codigo"));
        String listaId = parametros.get("lista");
        return Response.status(201).entity("{\"error\":" + !(EventsListsService.addEvent(listaId, codigoEvento)) + "}").build();
    }
    
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteList(String params) throws JsonProcessingException {
        Map<String, String> parametros = ManagementService.getPostParams(params);
        String listaId = parametros.get("listaId");
        String userId = this.loggedUser;
        return Response.status(201).entity(EventsListsService.deleteList(listaId, userId)).build();
    }

}
