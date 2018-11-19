package controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.User;

import java.io.IOException;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    User loggedUser;
    
    public EventsListsController(@Context ContainerRequestContext crc){
        this.loggedUser = UserService.currentUser(crc);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLists() throws JsonProcessingException {
        return Response.status(201).entity(EventsListsService.getAllLists()).build();
    }

    /**
     * busca las listas de un usuario incluyendo los eventos
     * 
     * @param uriDetails
     * @param crc
     * @return
     * @throws JsonProcessingException 
     */
    @Path("/getFromUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListsFromUser(@Context UriInfo uriDetails, @Context ContainerRequestContext crc) throws JsonProcessingException {
        String username = uriDetails.getQueryParameters().get("userId").get(0);
//        User loggedUser = UserService.currentUser(crc);
        System.out.println(this.loggedUser.getUserName());
        System.out.println(this.loggedUser.getId().toHexString());
        return Response.ok(EventsListsService.getByUserId(this.loggedUser.getId().toHexString())).build();
    }
    
    /**
     * busca las listas de un usuario sin los eventos para que sea más liviana y más rápida
     * 
     * @param uriDetails
     * @param crc
     * @return
     * @throws JsonProcessingException 
     */
    @Path("/getCleanListsFromUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCleanListsFromUser(@Context UriInfo uriDetails, @Context ContainerRequestContext crc) throws JsonProcessingException {
        String username = uriDetails.getQueryParameters().get("userId").get(0);
//        User loggedUser = UserService.currentUser(crc);
        System.out.println(this.loggedUser.getUserName());
        System.out.println(this.loggedUser.getId().toHexString());
        return Response.ok(EventsListsService.getCleanListsByUserId(this.loggedUser.getId().toHexString())).build();
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
//        User loggedUser = UserService.currentUser(crc);

        System.out.println(this.getClass().getName() + ":: username = " + username);
        System.out.println(this.getClass().getName() + ":: params = " + params);

        Map<String, String> parametros = ManagementService.getPostParams(params);

        String nombreLista = parametros.get("nombreLista");
        System.out.println(this.getClass().getName() + ":: nombreLista = " + nombreLista);

        nombreLista = nombreLista.replace("+", " ");

        System.out.println(this.getClass().getName() + ":: nombreLista = " + nombreLista);

        String userId = this.loggedUser.getId().toHexString();
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
        String userId = this.loggedUser.getId().toHexString();
        return Response.status(201).entity(EventsListsService.deleteList(listaId, userId)).build();
    }

}
