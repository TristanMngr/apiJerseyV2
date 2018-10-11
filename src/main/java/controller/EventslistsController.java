package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.EventsList;
import service.EventslistsService;
import service.ManagementService;

@Path("/eventsLists")
public class EventslistsController {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLists() throws JsonProcessingException {
        return Response.status(201).entity(EventslistsService.getAllLists()).build();
    }

    @Path("/getFromUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListsFromUser(@Context UriInfo uriDetails) throws JsonProcessingException {
        Integer userId = Integer.parseInt(uriDetails.getQueryParameters().get("userId").get(0));
        return Response.ok(EventslistsService.getByUserId(userId)).build();
    }

    /**
     *
     * @return imprime en pantalla el json de lassss listassss de eventos
     */
    @Path("/crearLista")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearLista(String params) throws JsonProcessingException {
        Map<String, String> parametros = ManagementService.getPostParams(params);
        String nombreLista = parametros.get("nombreLista");
        System.out.println(nombreLista);
        Integer userId = 1; //id del usuario logueado
        return Response.status(201).entity("{\"error\":" + !(EventslistsService.crearLista(nombreLista, userId)) + "}").build();
    }

    @Path("/agregarEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarEvento(String params) {
        Map<String, String> parametros = ManagementService.getPostParams(params);
        Long codigoEvento = Long.parseLong(parametros.get("codigo"));
        Long listaId = Long.parseLong(parametros.get("lista"));
//        EventslistsService.agregarEvento(listaId, codigoEvento);
        return Response.status(201).entity("{\"error\":" + !(EventslistsService.agregarEvento(listaId, codigoEvento)) + "}").build();
    }

}
