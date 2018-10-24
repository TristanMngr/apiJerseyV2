package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.EventsListDAO;
import org.bson.types.ObjectId;
import service.EventsListsService;
import service.ManagementService;
import service.UserService;
import model.User;

import org.bson.types.ObjectId;

@Path("/eventsLists")
public class EventsListsController {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLists() throws JsonProcessingException {
        return Response.status(201).entity(EventsListsService.getAllLists()).build();
    }

    /*@Path("/getUserLists")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserLists(@Context UriInfo uriDetails) throws JsonProcessingException {
        ObjectId userId = new ObjectId(uriDetails.getQueryParameters().get("userId").get(0));
        return Response.ok(EventsListsService.getByUserId(userId)).build();

        Integer userId = Integer.parseInt(uriDetails.getQueryParameters().get("userId").get(0));
//        System.out.println("user id: " + userId);
        return Response.status(201).entity(EventsListsService.getByUserId(userId)).build();
    }*/

    @Path("/getFromUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListsFromUser(@Context UriInfo uriDetails) throws JsonProcessingException {
        ObjectId userId = new ObjectId(uriDetails.getQueryParameters().get("userId").get(0));
        return Response.ok(EventsListsService.getByUserId(userId)).build();
    }

    /**
     * @return imprime en pantalla el json de lassss listassss de eventos
     */
    @Path("/crearLista")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearLista(String params) throws JsonProcessingException {
        Map<String, String> parametros  = ManagementService.getPostParams(params);
        String              nombreLista = parametros.get("nombreLista");
        System.out.println(nombreLista);
        ObjectId userId = new ObjectId(); //id del usuario logueado TODO: recibirlo por parámetro u obtenerlo del UserService
        return Response.status(201).entity(EventsListsService.crearLista(nombreLista, userId)).build();
    }

    @Path("/agregarEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarEvento(String params) throws IOException {
        Map<String, String> parametros = ManagementService.getPostParams(params);
        Long codigoEvento = Long.parseLong(parametros.get("codigo"));
        Integer listaId = Integer.parseInt(parametros.get("lista"));
//        EventslistsService.agregarEvento(listaId, codigoEvento);
        return Response.status(201).entity("{\"error\":" + !(EventsListsService.agregarEvento(listaId, codigoEvento)) + "}").build();
    }

}
