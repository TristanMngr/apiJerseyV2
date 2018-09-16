package controller;


import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Event;
import model.EventsList;
import service.EventbriteApi;
import service.EventsService;


@Path("/events")
public class EventsController {

	static final long idEventBrite = 17920884849L;

	private EventsService eventsService = new EventsService();

	public static String formatString(String text) {

		StringBuilder json = new StringBuilder();
		String indentString = "";

		for (int i = 0; i < text.length(); i++) {
			char letter = text.charAt(i);
			switch (letter) {
			case '{':
			case '[':
				json.append("\n" + indentString + letter + "\n");
				indentString = indentString + "\t";
				json.append(indentString);
				break;
			case '}':
			case ']':
				indentString = indentString.replaceFirst("\t", "");
				json.append("\n" + indentString + letter);
				break;
			case ',':
				json.append(letter + "\n" + indentString);
				break;

			default:
				json.append(letter);
				break;
			}
		}

		return json.toString();
	}

	public static List<Event> MaestroEventos = new ArrayList<Event>();
	public static List<EventsList> MaestroListasEventos = new ArrayList<EventsList>();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response get() {
		return Response.status(201).entity(this.eventsService.allEvents()).build();
	}

	@Path("/{eventID}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response produceJSON(@PathParam("eventID") Long id) {
		if (id == idEventBrite) {
			return Response.status(201).entity(formatString(EventbriteApi.getEvent(id.toString()))).build();
		} else {
			Event evento = this.eventsService.getEventById(id);
			return Response.status(201).entity(evento).build();
		}
//		if (this.eventsService.getEventById(id).contains(id)) {
//			return Response.status(201).entity(this.eventsService.getEventById(id)).build();
//		} else {
//			System.out.println("ID = " + id);
//			if (id.equals("17920884849"))
//				return Response.status(201).entity(formatString(EventbriteApi.getEvent(id.toString()))).build();
//
//			return Response.status(201).entity("Event: " + id + " not found!").build();
//		}
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
	 * 
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
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response crearListaEventos(@PathParam("nombreLista") String nombre) {

		MaestroListasEventos.add(new EventsList(nombre));
		// System.out.println("Se creó el evento " + listaEventos.get(0).getFecha());
		return Response.status(201).entity(MaestroListasEventos).build();
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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarEventosLista(@PathParam("nombreLista") String nombreLista,
			@PathParam("nombreEvento") String nombreEvento) {
		List<EventsList> coleccionListas = this.buscarListaEventosPorNombre(nombreLista);

		for (EventsList lista : coleccionListas) {
			lista.agregarEvento(this.buscarEventosPorNombre(nombreEvento));
		}

		// System.out.println("Se creó el evento " + evento);
		return Response.status(201).entity(MaestroListasEventos).build();

	}

	/*
	 * *************************** métodos auxiliares
	 * ***********************************
	 */

	private List<Event> buscarEventosPorNombre(String nombre) {
		List<Event> result = new ArrayList<Event>();
		for (Event evento : MaestroEventos) {
			if (evento.seLlama(nombre)) {
				result.add(evento);
			}
		}
		return result;

		/*
		 * //no me funciona el filter List<Event> eventoBuscado =
		 * MaestroEventos.stream().filter(evento -> evento.seLlama(nombre))
		 * .collect(Collectors.<Event> toList()); //System.out.println(eventoBuscado);
		 * return Response.status(201).entity(eventoBuscado).build();
		 */
	}

	/**
	 * busca una lista de eventos por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	private List<EventsList> buscarListaEventosPorNombre(String nombre) {
		List<EventsList> result = new ArrayList<EventsList>();
		for (EventsList lista : MaestroListasEventos) {
			if (lista.seLlama(nombre)) {
				result.add(lista);
			}
		}
		/*
		 * MaestroListasEventos.forEach(lista -> { if (lista.seLlama(nombre)) {
		 * //result.add(lista); } });
		 */
		return result;
	}
}
