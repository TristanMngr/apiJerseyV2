package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import model.EventsList;
import org.bson.types.ObjectId;

public class EventsListsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String create(String nombre, Integer userId) throws JsonProcessingException {
        ObjectId userIdObj = new ObjectId(Integer.toHexString(userId));
        EventsList listaCreada = ManagementService.getEventsListDAO().create(nombre, userIdObj);
        if (listaCreada == null) {
            return "{\"error\":1}";
        }
        return "{\"error\":0,\"lista\":" + mapper.writeValueAsString(listaCreada) + "}";
    }

    public static String getAllLists() throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
    }

    public static String getByUserId(Integer userId) throws JsonProcessingException {
        ObjectId userIdObj = new ObjectId(Integer.toHexString(userId));
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getByUserId(userIdObj));
    }

    public static EventsList getByListaId(Integer listaId) {
        ObjectId listaIdObj = new ObjectId(Integer.toHexString(listaId));
        return ManagementService.getEventsListDAO().getByListaId(listaIdObj);
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean addEvent(Integer listaId, Long codigoEvento) throws IOException {
        ObjectId listaIdObj = new ObjectId(Integer.toHexString(listaId));
        EventsList lista = ManagementService.getEventsListDAO().getByListaId(listaIdObj);
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
