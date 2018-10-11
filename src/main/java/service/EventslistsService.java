package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import model.EventsList;

public class EventslistsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static boolean crearLista(String nombre, Integer userId) {
        return ManagementService.getEventsListDAO().create(nombre, userId);
    }

    public static String getAllLists() throws JsonProcessingException {
//        List<EventsList> listas = eventsListDAO.getAllLists();
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
//        return jsonInString;
    }

    public static String getByUserId(Integer userId) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getByUserId(userId));
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean agregarEvento(Long listaId, Long codigoEvento) {
        EventsList lista = ManagementService.getEventsListDAO().get(listaId);
        return ManagementService.getEventsListDAO().addEventToList(lista, codigoEvento);
    }
}
