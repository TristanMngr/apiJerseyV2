package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import model.EventsList;

import dao.EventsListDAO;

public class EventslistsService {

    ObjectMapper mapper = new ObjectMapper();
    EventsListDAO eventsListDAO = new EventsListDAO();

    public String getAllLists() throws JsonProcessingException {
//        List<EventsList> listas = eventsListDAO.getAllLists();
        return mapper.writeValueAsString(eventsListDAO.getAllLists());
//        return jsonInString;
    }
    
    public String getByUserId(Long userId) throws JsonProcessingException {
        return mapper.writeValueAsString(eventsListDAO.getByUserId(userId));
    }

    public Boolean seLlama(EventsList lista, String nombre) {
        return lista.getNombre().equals(nombre);
    }

    public void agregarEvento(EventsList lista, List<Long> eventoIds) {
        for (Long eventoId : eventoIds) {
            eventsListDAO.addEventToList(lista, eventoId);
        }
    }
}
