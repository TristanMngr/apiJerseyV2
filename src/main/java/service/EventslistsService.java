package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import java.util.List;
import model.EventsList;

public class EventslistsService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String crearLista(String nombre, Integer userId) throws JsonProcessingException {
        Long listaId = ManagementService.getEventsListDAO().create(nombre, userId);
        if (listaId != 0) {
            EventsList listaCreada = getById(listaId);
            return "{\"error\":0,\"lista\":" + mapper.writeValueAsString(listaCreada) + "}";
        }
        return "{\"error\":1}";
    }

    public static String getAllLists() throws JsonProcessingException {
//        List<EventsList> listas = eventsListDAO.getAllLists();
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getAllLists());
//        return jsonInString;
    }

    public static String getByUserId(Integer userId) throws JsonProcessingException {
        return mapper.writeValueAsString(ManagementService.getEventsListDAO().getByUserId(userId));
    }

    public static EventsList getById(Long id) {
        return ManagementService.eventsListsDAO.getById(id);
    }

    public static Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public static Boolean agregarEvento(Long listaId, Long codigoEvento) throws IOException {
        EventsList lista = ManagementService.getEventsListDAO().getById(listaId);
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
