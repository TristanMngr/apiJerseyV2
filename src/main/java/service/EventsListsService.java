package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.EventsList;
import model.User;
import org.bson.types.ObjectId;
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

}
