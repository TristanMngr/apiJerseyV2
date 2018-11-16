package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.EventsList;
import model.Session;
import model.User;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateResults;
import static service.EventbriteService.getJsonEventByID;

public class EventsListsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String create(String nombre, String userId) throws JsonProcessingException {
        EventsList listaCreada = new EventsList(userId, nombre);
        ManagementService.getEventsListDAO().create(listaCreada);
        User user = UserService.addListToUser(listaCreada, userId);
        user.getEventsLists().stream().forEach(list -> buildHexId(list));
        return "{\"error\":0,\"lista\":" + mapper.writeValueAsString(listaCreada) + ",\"user\":" + mapper.writeValueAsString(user) + "}";
    }

    public static String getAllLists() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
    }

    public static String getByUserId(String userId) throws JsonProcessingException {
        User user = UserService.getUserObjectById(userId);
        user.getEventsLists().stream().forEach(list -> buildHexId(list));

        user.getEventsLists().stream().forEach(list -> list.setEventsObj(list.getEvents().stream().map(event -> getJsonEventByID(event.toString())).collect(Collectors.toList())));
        
        return mapper.writeValueAsString(user);
    }

    public static EventsList getByListaId(String listaId) {
        return ManagementService.getEventsListDAO().getByListaId(new ObjectId(listaId));
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean addEvent(String listaId, Long codigoEvento) throws IOException {
        EventsList lista = ManagementService.getEventsListDAO().getByListaId(new ObjectId(listaId));
//        EventBrite evento = EventbriteService.getEventByID(codigoEvento);
        List<Long> events = lista.getEvents();
        events.add(codigoEvento);
        lista.setEvents(events);
        ManagementService.getEventsListDAO().saveEventToList(lista, events);
        return true;
    }

    public static String deleteList(String listaId, String userId) {
        EventsList list = getByListaId(listaId);
        //eliminar la lista
        ManagementService.getEventsListDAO().deleteEventsLists(new ObjectId(listaId));
        //sacar la referencia del usuario
        UpdateResults result = ManagementService.getUserDAO().removeEventsListFromUser(new ObjectId(userId), list);
//        result.
        return "{\"error\":0}";
    }

    private static EventsList buildHexId(EventsList list) {
        list.setHexId(list.getId().toHexString());
        return list;
    }
    
    public static List<Long> getListOfEventsByUserAndListName(String username, String lista){
    	
    	User user = ManagementService.getUserDAO().getUserByName(username);
    	List<EventsList> listados = user.getEventsLists();
    	
    	EventsList listadoEventos = null;

    	for(EventsList listado : listados) {
    		ObjectId id = listado.getId();
    		Query<EventsList> query = ManagementService.getEventsListDAO().getDatastore().createQuery(EventsList.class).field("_id").equal(id);
    		listadoEventos = query.get();
    		if(listadoEventos.getNombre().equals(lista))
    			break;
    	} 	
    	    	
		return listadoEventos.getEvents();
    	
    };
    
    public static int getCountUsersWithEvent(Long codigo){
		int cantidad = 0;
		try {
			String users = UserService.getAllNonAdminUsers();
			JSONArray jsonUsers = new JSONArray(users);
			for (int i = 0; i < jsonUsers.length(); i++) {
				JSONObject userJSONObj = jsonUsers.getJSONObject(i);
				JSONArray eventsListJSONObj = new JSONArray(userJSONObj.get("eventsLists").toString());
				for (int j = 0; j < eventsListJSONObj.length(); j++) {
					JSONObject eventsObj = eventsListJSONObj.getJSONObject(j);
					JSONArray jsonEventsList = new JSONArray(eventsObj.get("events").toString());
					List<Object> eventos = jsonEventsList.toList();
					if(eventos.contains(codigo))
						cantidad++;
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	
    	return cantidad;
    }

}
