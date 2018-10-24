package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import java.util.List;
import model.EventsList;
import org.bson.types.ObjectId;

public class EventsListsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String crearLista(String nombre, ObjectId userId) throws JsonProcessingException {
        EventsList listaCreada = ManagementService.getEventsListDAO().create(nombre, userId);
        if (listaCreada == null) {
            return "{\"error\":1}";
        }
        return "{\"error\":0,\"lista\":" + mapper.writeValueAsString(listaCreada) + "}";
    }

    public static String getAllLists() throws JsonProcessingException {
//        List<EventsList> listas = eventsListDAO.getAllLists();
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
//        return jsonInString;
    }

    public static String getByUserId(ObjectId userId) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getByUserId(userId));
    }

    public static EventsList getByListaId(Integer listaId) {
        return ManagementService.getEventsListDAO().getByListaId(listaId);
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean agregarEvento(Integer listaId, Long codigoEvento) throws IOException {
        EventsList lista = ManagementService.getEventsListDAO().getByListaId(listaId);
        EventBrite evento = EventbriteService.getEventByID(codigoEvento);
        return ManagementService.getEventsListDAO().addEventToList(lista, evento);
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
