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

import com.google.gson.*;

import eventbrite.EventBrite;
import model.Event;
import model.EventsList;
import service.EventbriteApi;
import service.EventsService;

@Path("/events")
public class EventsController {

	static final long idEventBrite = 17920884849L;

	private EventsService eventsService = new EventsService();
	private EventBrite eventBrite = new EventBrite();
	final Gson gson = new Gson();

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

	/**
	 * buscar un evento por ID,
	 * 
	 * @param params
	 * @return
	 */
	@Path("/buscarEvento")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Response buscarEvento(String param) {
	//public Viewable buscarEvento(String param) {
		Long id = 0L;
		String searchPattern = "";
		Map<String, String> model = new HashMap<String, String>();
		model.put("codigo", "");
		model.put("nombre", "");
		model.put("fecha", "");
		model.put("hora", "");

		List<String> parametros = Arrays.asList(param.split("=", -1));
		
		if(parametros.get(1).matches("\\d+"))
			id = Long.valueOf(parametros.get(1));
		else
			searchPattern = parametros.get(1);
		
		Date date = new Date();
		
		// TODO: Cristhian: Solo por testing.
		if (id == idEventBrite) {
			eventBrite = gson.fromJson(EventbriteApi.getEventByID(id.toString()), EventBrite.class);
			model.put("codigo", eventBrite.getId().toString());
			model.put("nombre", eventBrite.getName().getText());
						
			try {
				DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				date = iso8601.parse(eventBrite.getStart().getLocal());
				
				SimpleDateFormat simpleDateFormat_Date = new SimpleDateFormat("yyyy-MM-dd");
				String fechaEvento = simpleDateFormat_Date.format(date);
				
				SimpleDateFormat simpleDateFormat_Time = new SimpleDateFormat("HH:mm:ss");
				String horaEvento = simpleDateFormat_Time.format(date);
				model.put("fecha", fechaEvento);
				model.put("hora", horaEvento);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		else if ( !searchPattern.equals(""))
		{
			// TODO: No pude hacer andar el Viewable con un listado.
			String results = EventbriteApi.getEventByName(searchPattern);
			List<Event> listado = eventsService.getFromPagination(results);
			//return new Viewable("/jsp/test", model);
			return Response.status(201).entity(listado).build();
		}
		else {
			Event evento = this.eventsService.getEventById(id);
			if (evento != null) {
				model.put("codigo", evento.getId().toString());
				model.put("nombre", evento.getNombre());
				model.put("fecha", evento.getFecha());
				model.put("hora", evento.getHora());
			}
			
		}
		//return new Viewable("/jsp/eventos/evento", model);
		return Response.ok(new Viewable("/jsp/eventos/evento", model)).build();
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
	 * Busca eventos por varios parametros
	 * 
	 * @param nombre
	 * @return lista de eventos
	 */
	@Path("/buscarEventos")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Viewable buscarEventos(String params) {
		Long id = 0L;
//		String nombre = "";
		Map<String, String> model = new HashMap<String, String>();

		List<String> parametros = Arrays.asList(params.split("&", -1));
//		List<String> valores=parametros.stream().map(p->p.split("=",1)).collect(Collector.toList());
		List<List<String>> valores = new ArrayList<List<String>>();
		for (String p : parametros) {
			valores.add(Arrays.asList(p.split("=", -1)));
		}
		id = Long.valueOf(valores.get(0).get(1));
//		nombre = valores.get(1).get(1);

		if (id == idEventBrite) {
			eventBrite = gson.fromJson(EventbriteApi.getEventByID(id.toString()), EventBrite.class);
			System.out.println("algo del json" + eventBrite.getSubcategoryId());
			return new Viewable("/index", model);
//			return Response.status(201).entity(formatString(EventbriteApi.getEvent(id.toString()))).build();
//			model.put("nombre", "Hello");
//			model.put("descripcion", "World! I'm index.jsp");
//			model.put("start",);
//			model.put("finish");
		} else {
			Event evento = this.eventsService.getEventById(id);
			model.put("codigo", evento.getId().toString());
			model.put("nombre", evento.getNombre());
			model.put("fecha", evento.getFecha());
			model.put("hora", evento.getHora());
			return new Viewable("/eventos/evento", model);
		}
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
