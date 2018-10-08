package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Properties;
//import java.util.stream.Collector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import eventbrite.EventBrite;
import model.Event;
import model.EventsList;
import service.EventbriteService;
import service.EventsService;
import service.ManagementService;

@Path("/events")
public class EventsController {

    static final long idEventBrite = 17920884849L;

    private ManagementService managementService = new ManagementService();
    private EventsService eventsService = new EventsService();
    private EventBrite eventBrite = new EventBrite();

    @GET
    @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    public Viewable mainEventos() {
        Map<String, String> model = new HashMap<String, String>();
        String categories = EventbriteService.getCategories();
        model.put("categories", categories);
        return new Viewable("/jsp/eventos/index", model);
    }

    /**
     * Busca eventos por varios parametros
     *
     * @param params
     * @return lista de eventos
     */
    @Path("/buscarEventos")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEventos(String params) {
        String eventosEncontrados = "";
        Map<String, String> paramsEventBrite = new HashMap<String, String>();
        Map<String, String> parametros = managementService.getPostParams(params);

        paramsEventBrite.put("codigo", parametros.get("codigo"));
        paramsEventBrite.put("nombre", parametros.get("nombre"));
        paramsEventBrite.put("categoryId", parametros.get("categoryId"));
        paramsEventBrite.put("fechaDesde", parametros.get("desde"));
        paramsEventBrite.put("fechaHasta", parametros.get("hasta"));
        eventosEncontrados = EventbriteService.searchEvents(paramsEventBrite);
        return Response.ok(eventosEncontrados).build();
    }

}
