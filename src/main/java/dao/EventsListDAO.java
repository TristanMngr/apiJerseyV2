package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


import com.mongodb.WriteResult;
import model.Evento;
import model.EventsList;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

public class EventsListDAO extends BasicDAO<EventsList, ObjectId> {

    private final AtomicLong counter = new AtomicLong();

    // Dummy database. Initialize with some dummy values.
    private List<EventsList> listas;

    public EventsListDAO(Datastore dataStore) {
        super(dataStore);
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
     *
     * @param id
     * @return
     */
    public List<EventsList> getByUserId(Integer userId) {
        Query<EventsList> query = getDatastore().find(EventsList.class, "userId", userId);
        List<EventsList> eventsLists = query.asList();
        return eventsLists;
    }

    public Boolean addEventToList(EventsList lista, Long eventoId) {
        /*return lista.getEventos().add(eventoId);*/
        return lista.getEventos().add(new Evento());
    }

    /**
     * Create new EventsList in dummy database. Updates the id and insert new
     * EventsList in list.
     *
     * @param lista EventsList object
     * @return lista object with updated id
     */
    public boolean create(String nombre, Integer userId) {
        return listas.add(new EventsList(userId, nombre));
    }


    /**
     * Delete the EventsList object from dummy database. If EventsList not found
     * for given id, returns null.
     *
     * @param id the EventsList id
     * @return id of deleted EventsList object
     */
    public WriteResult deleteEventsLists(ObjectId id) {
        WriteResult writeResult = getDatastore().delete(EventsList.class, id);
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
    public EventsList update(Long id, EventsList lista) {

        for (EventsList l : listas) {
            if (l.getId().equals(id)) {
                lista.setId(l.getId());
                listas.remove(l);
                listas.add(lista);
                return lista;
            }
        }

        return null;
    }

}
