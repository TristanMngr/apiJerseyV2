package dao;

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

    private final AtomicLong counter = new AtomicLong();
    private UserDAO userDAO;

    // Dummy database. Initialize with some dummy values.
    private List<EventsList> listas;

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
        Query<EventsList> query       = getDatastore().find(EventsList.class);
        List<EventsList>  eventsLists = query.asList();
        return eventsLists;
    }

    /**
     * @param userId
     * @return
     */
    public List<EventsList> getByUserId(ObjectId userId) {
        Query<EventsList> query       = getDatastore().find(EventsList.class, "userId", userId);
        List<EventsList> eventsLists = query.asList();

        return eventsLists;
    }

    /*public Boolean addEventToList(EventsList lista, Long eventoId) {
        *//*return lista.getEventos().add(eventoId);*//*
        return lista.getEventLists().add(new Evento());

    }*/

    /**
     * Create new EventsList in dummy database. Updates the id and insert new
     * EventsList in list.
     *
     * @param nombre EventsList object
     * @return lista object with updated id
     */
    public boolean create(String nombre, ObjectId userId) {
        User user = userDAO.getByUserId(userId);

        if (user == null) {
            return false;
        }

        EventsList eventsList = new EventsList(nombre);
        eventsList.setUserId(userId);
        getDatastore().save(eventsList);

        user.setEventos(Arrays.asList(eventsList));
        getDatastore().save(user);
        return true;
    }


    /**
     * Delete the EventsList object from dummy database. If EventsList not found
     * for given id, returns null.
     *
     * @param listId the EventsList id
     * @return id of deleted EventsList object
     */
    public WriteResult deleteEventsLists(ObjectId listId) {
        WriteResult writeResult = getDatastore().delete(EventsList.class, listId);
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
