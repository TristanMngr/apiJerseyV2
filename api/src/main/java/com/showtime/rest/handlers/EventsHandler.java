package com.showtime.rest.handlers;

import com.showtime.rest.Event;
import com.showtime.rest.EventsList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventsHandler {

	public static List<String> testListEvents = Arrays.asList("evento 1", "evento 2", "evento 3");
	public static List<Event> MaestroEventos = new ArrayList<Event>();
	public static List<EventsList> listasEventos = new ArrayList<EventsList>();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response get() {
		return Response.status(201).entity(testListEvents).build();
	}

	@Path("{eventID}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response produceJSON(@PathParam("eventID") int id) {
		if (testListEvents.contains(String.valueOf(id))) {
			return Response.status(201).entity("Event: " + String.valueOf(id) + " was found!").build();
		} else {
			return Response.status(201).entity("Event: " + String.valueOf(id) + " not found!").build();
		}
	}

	@Path("/cantidad")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAmountEvents() {
		return Response.status(201).entity("Hay " + MaestroEventos.size() + " eventos").build();
	}

	@Path("/crearEvento/{nombre}/{fecha}/{hora}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response crearEvento(@PathParam("nombre") String nombre, @PathParam("fecha") String fecha,
			@PathParam("hora") String hora) {

		Event nuevoEvento = new Event(nombre, fecha, hora);
		MaestroEventos.add(nuevoEvento);
		// System.out.println("Se creó el evento " + listaEventos.get(0).getFecha());
		return Response.status(201).entity(MaestroEventos).build();
	}

	/**
	 * Busca eventos por nombre completo
	 * @param nombre
	 * @return lista de eventos
	 */
	@Path("/buscarEvento/{nombreEvento}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response buscarEvento(@PathParam("nombreEvento") String nombre) {
		return Response.status(201).entity(this.buscarEventosPorNombre(nombre)).build();
	}
	
	/**
	 * 
	 * @param nombre nombre de la lista de eventos
	 * @return imprime en pantalla el json de lassss listassss de eventos
	 */
	@Path("/crearListaEventos/{nombreLista}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response crearListaEventos(@PathParam("nombreLista") String nombre) {

		listasEventos.add(new EventsList(nombre));
		// System.out.println("Se creó el evento " + listaEventos.get(0).getFecha());
		return Response.status(201).entity(listasEventos).build();
	}

	

	/*
	 * @Path("/agregarEventosLista/{nombreLista}/{nombreEvento}")
	 * 
	 * @GET
	 * 
	 * @Produces({ MediaType.APPLICATION_JSON }) public Response
	 * agregarEventosLista(@PathParam("nombreLista") String
	 * lista,@PathParam("nombreEvento") String evento) {
	 * 
	 * //listasEventos.get(lista).add(new EventsList(nombre));
	 * System.out.println("Se creó el evento " + evento); return
	 * Response.status(201).entity(listasEventos.get(lista)).build();
	 * 
	 * }
	 */
	
	
	/* *************************** métodos auxiliares *********************************** */
	private List<Event> buscarEventosPorNombre(String nombre){
		List<Event> result = new ArrayList<>();
		for (Event evento : MaestroEventos) {
			if (evento.seLlama(nombre)) {
				result.add(evento);
			}
		}
		return result;
		
		/*
		 * List<Event> eventoBuscado = MaestroEventos.stream().filter(evento ->
		 * evento.seLlama(nombre)) .collect(Collectors.<Event> toList());
		 * //System.out.println(eventoBuscado); return
		 * Response.status(201).entity(eventoBuscado).build();
		 */
	}
}
