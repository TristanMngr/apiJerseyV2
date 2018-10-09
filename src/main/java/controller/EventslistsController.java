package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.EventsList;
import service.EventslistsService;
import service.ManagementService;

@Path("/eventsLists")
public class EventslistsController {

    public static List<EventsList> MaestroListasEventos = new ArrayList<EventsList>();

    private ManagementService managementService = new ManagementService();
    private EventslistsService eventslistsService = new EventslistsService();

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLists() throws JsonProcessingException {
        return Response.status(201).entity(eventslistsService.getAllLists()).build();
    }
    
    @Path("/getFromUser/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByUserId(@PathParam("userId") Long userId) throws JsonProcessingException {
        return Response.status(201).entity(eventslistsService.getByUserId(userId)).build();
    }

    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     * ***************************************************************************************
     */
    /**
     *
     * @param nombre nombre de la lista de eventos
     * @return imprime en pantalla el json de lassss listassss de eventos
     */
    @Path("/crearListaEventos/{nombreLista}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response crearListaEventos(@PathParam("nombreLista") String nombre) {

//        MaestroListasEventos.add(new EventsList(nombre));
        // System.out.println("Se creó el evento " + listaEventos.get(0).getFecha());
//        return Response.status(201).entity(MaestroListasEventos).build();
        return Response.status(201).entity("algo").build();
    }

    /**
     * agrega un evento a una lista existente
     *
     * @param nombreLista
     * @param nombreEvento
     * @return
     */
    @Path("/agregarEventosLista/{nombreLista}/{nombreEvento}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response agregarEventosLista(@PathParam("nombreLista") String nombreLista, @PathParam("nombreEvento") String nombreEvento) {
        List<EventsList> coleccionListas = this.buscarListaEventosPorNombre(nombreLista);

        for (EventsList lista : coleccionListas) {
//            lista.agregarEvento(this.buscarEventosPorNombre(nombreEvento));
        }

        // System.out.println("Se creó el evento " + evento);
        return Response.status(201).entity("MaestroListasEventos").build();

    }

    /**
     * busca una lista de eventos por nombre
     *
     * @param nombre
     * @return
     */
    private List<EventsList> buscarListaEventosPorNombre(String nombre) {
        List<EventsList> result = new ArrayList<EventsList>();
//        for (EventsList lista : MaestroListasEventos) {
//            if (lista.seLlama(nombre)) {
//                result.add(lista);
//            }
//        }
        /*
		 * MaestroListasEventos.forEach(lista -> { if (lista.seLlama(nombre)) {
		 * //result.add(lista); } });
         */
        return result;
    }
}
