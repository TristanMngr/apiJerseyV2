package dao;

import eventbrite.EventBrite;
import java.util.ArrayList;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.mongodb.WriteResult;
import model.Evento;
import model.EventsList;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import javax.swing.event.ListDataEvent;

public class EventsListDAO extends BasicDAO<EventsList, ObjectId> {

//    private final AtomicLong counter = new AtomicLong();
    private UserDAO userDAO;

    // Dummy database. Initialize with some dummy values.
//    private List<EventsList> listas;
    public EventsListDAO(Datastore dataStore, UserDAO userDAO) {
        super(dataStore);
        userDAO = userDAO;
    }

    /**
     * Returns list of eventslists from dummy database.
     *
     * @return list of eventslists
     */
    public List<EventsList> getAllLists() {
        Query<EventsList> query = getDatastore().find(EventsList.class);
        List<EventsList> eventsLists = query.asList();
        return eventsLists;
    }

    /**
     * @param userId
     * @return
     */
    public List<EventsList> getByUserId(ObjectId userId) {
        Query<EventsList> query = getDatastore().find(EventsList.class, "userId", userId);
        List<EventsList> eventsLists = query.asList();

        return eventsLists;
    }

    public Boolean addEventToList(EventsList lista, EventBrite evento) {
        /*return lista.getEventos().add(new Evento(eventoId));
        return lista.getEventos().add(new Evento());*/
        return false;
    }

    /**
     * Return EventsList object for given id from dummy database. If EventsList
     * is not found for id, returns null.
     *
     * @param id EventsList id
     * @return EventsList object for given id
     */
//    public EventsList getById(Long id) {
//        return listas.stream().filter(elem -> elem.getId().equals(id)).collect(Collectors.toList()).get(0);
//    }
    public EventsList getByListaId(ObjectId listaId) {
        Query<EventsList> query = getDatastore().find(EventsList.class, "listaId", listaId);
        EventsList eventsLists = query.asList().get(0);
        return eventsLists;
    }

    /**
     * Create new EventsList
     * Add the list in User object
     * 
     * @param nombre String object
     * @param userId ObjectId object
     * @return listaId
     */
    public EventsList create(String nombre, ObjectId userId) {
        User user = userDAO.getByUserId(userId);

        if (user == null) {
            return null;
        }

        EventsList eventsList = new EventsList(nombre);

//        eventsList.setListaId(1);   //TODO: cómo hacer el id autoincrement
        eventsList.setUserId(userId);
        getDatastore().save(eventsList);

        user.setEventsLists(Arrays.asList(eventsList));
        getDatastore().save(user);

        return eventsList;
    }

    /**
     * Delete the EventsList object from dummy database. If EventsList not found
     * for given id, returns null.
     *
     * @param listaId ObjectId
     * @return id of deleted EventsList object
     */
    public WriteResult deleteEventsLists(ObjectId listaId) {
        WriteResult writeResult = getDatastore().delete(EventsList.class, listaId);
        return writeResult;
    }

    /**
     * Return EventsList object for given id from dummy database. If EventsList
     * is not found for id, returns null.
     *
     * @param id EventsList id
     * @return EventsList object for given id
     */
    public EventsList getEventsLists(ObjectId id) {
        EventsList eventsList = getDatastore().get(EventsList.class, id);

        return eventsList;
    }

    //TODO persisting for update
    /**
     * Update the EventsList object for given id in dummy database. If
     * EventsList not exists, returns null
     *
     * @param id
     * @param lista
     * @return EventsList object with id
     */
    /*public EventsList update(Long id, EventsList lista) {

        for (EventsList l : listas) {
            if (l.getId().equals(id)) {
                lista.setId(l.getId());
                listas.remove(l);
                listas.add(lista);
                return lista;
            }
        }

        return null;
    }*/
}
