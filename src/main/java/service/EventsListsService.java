package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import model.EventsList;
import model.User;
import org.bson.types.ObjectId;

public class EventsListsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String create(String nombre, String userId) throws JsonProcessingException {
        EventsList listaCreada = new EventsList(userId, nombre);
        ManagementService.getEventsListDAO().create(listaCreada);
        User user = UserService.addListToUser(listaCreada, userId);
        return "{\"error\":0,\"lista\":" + mapper.writeValueAsString(listaCreada) + ",\"user\":" + mapper.writeValueAsString(user) + "}";
    }

    public static String getAllLists() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
    }

    public static String getByUserId(String userId) throws JsonProcessingException {
        return mapper.writeValueAsString(UserService.getUserObjectById(userId));
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

}
