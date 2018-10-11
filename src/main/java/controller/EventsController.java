package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eventbrite.EventBrite;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import service.EventbriteService;
import service.EventsService;
import service.EventslistsService;
import service.ManagementService;

@Path("/events")
public class EventsController {

    private ManagementService managementService = new ManagementService();
    private EventsService eventsService = new EventsService();
    private EventslistsService eventslistsService = new EventslistsService();
    private EventBrite eventBrite = new EventBrite();

    /**
     * Busca todas las categorías de eventos de EventBrite
     *
     * @return json
     */
    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        return Response.ok(EventbriteService.getAllCategories()).build();
    }

    @Path("/eventsLists")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventsListsByUser(@Context UriInfo uriDetails) throws JsonProcessingException {
        Long userId = Long.parseLong(uriDetails.getQueryParameters().get("userId").get(0));
        return Response.ok(eventslistsService.getByUserId(userId)).build();
    }

    /**
     * Busca eventos por varios parametros
     *
     * @param params
     * @return lista de eventos
     */
    @Path("/buscarEventos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEventos(@Context UriInfo uriDetails) {
        Map<String, String> paramsEventBrite = new HashMap<String, String>();
        paramsEventBrite.put("codigo", uriDetails.getQueryParameters().get("codigo").get(0));
        paramsEventBrite.put("nombre", uriDetails.getQueryParameters().get("nombre").get(0));
        paramsEventBrite.put("categoryId", uriDetails.getQueryParameters().get("categoryId").get(0));
        paramsEventBrite.put("fechaDesde", uriDetails.getQueryParameters().get("desde").get(0));
        paramsEventBrite.put("fechaHasta", uriDetails.getQueryParameters().get("hasta").get(0));
        return Response.ok(EventbriteService.searchEvents(paramsEventBrite)).build();
    }

}
