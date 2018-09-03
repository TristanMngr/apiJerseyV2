package com.showtime.rest.handlers;

import com.showtime.rest.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventsHandler {

	public static List<String> testListEvents = Arrays.asList("evento 1", "evento 2", "evento 3");
	public static List<Event> listaEventos = new ArrayList<Event>();

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
		return Response.status(201).entity("Hay " + listaEventos.size() + " eventos").build();
	}

	@Path("/crearEvento/{nombre}/{fecha}/{hora}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response crearEvento(@PathParam("nombre") String nombre, @PathParam("fecha") String fecha,
			@PathParam("hora") String hora) {

		Event nuevoEvento = new Event(nombre, fecha, hora);
		listaEventos.add(nuevoEvento);
		//System.out.println("Se creó el evento " + listaEventos.get(0).getFecha());
		return Response.status(201).entity(listaEventos).build();
	}
}
