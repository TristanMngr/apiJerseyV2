package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import model.EventsList;
import org.bson.types.ObjectId;

public class EventsListsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static boolean crearLista(String nombre, ObjectId userId) {
        return ManagementService.getEventsListDAO().create(nombre, userId);
    }

    public static String getAllLists() throws JsonProcessingException {
//        List<EventsList> listas = eventsListDAO.getAllLists();
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
//        return jsonInString;
    }

    public static String getByUserId(ObjectId userId) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getByUserId(userId));
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean agregarEvento(ObjectId listaId, Long codigoEvento) {
        EventsList lista = ManagementService.getEventsListDAO().getEventsLists(listaId);
        return ManagementService.getEventsListDAO().addEventToList(lista, codigoEvento);
    }

    /**
     * busca los datos de los eventos según el código y arma el objeto lisa
     * completo con esos datos
     *
     * @param EventsList lista
     * @return EventsList listaArmada
     */
//    private static EventsList armarListaEventos(EventsList lista) {
//        lista.getEventos().stream().map(codigo -> EventbriteService.getEventByID(codigo.toString()));
//
//    }
}
