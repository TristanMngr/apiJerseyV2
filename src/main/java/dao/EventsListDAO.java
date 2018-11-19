package dao;

import java.util.List;

import com.mongodb.WriteResult;
import model.EventsList;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

public class EventsListDAO extends BasicDAO<EventsList, ObjectId> {

    private final UserDAO userDAO;

    public EventsListDAO(Datastore dataStore, UserDAO userDAO) {
        super(dataStore);
        this.userDAO = userDAO;
    }

    /**
     * @return list of eventslists
     */
    public List<EventsList> getAllLists() {
        Query<EventsList> query = getDatastore().find(EventsList.class);
        return query.asList();
    }

    /**
     * @param userId
     * @return lists for given user
     */
    public List<EventsList> getByUserId(String userId) {
        Query<EventsList> query = getDatastore().find(EventsList.class, "userId", userId);
        return query.asList();
    }

    /**
     *
     * @param listaId ObjectId
     * @return EventsList object for given id
     */
    public EventsList getByListaId(ObjectId listaId) {
        Query<EventsList> query = getDatastore().find(EventsList.class, "_id", listaId);
        return query.asList().get(0);
    }

    /**
     * Create new EventsList Add the list in User object
     *
     * @param listaNueva EventsList object
     */
    public Key<EventsList> create(EventsList listaNueva) {
        return getDatastore().save(listaNueva);
    }

    /**
     * @param listaId ObjectId
     * @return id of deleted EventsList object
     */
    public WriteResult deleteEventsLists(ObjectId listaId) {
        WriteResult writeResult = getDatastore().delete(EventsList.class, listaId);
        return writeResult;
    }

    public UpdateResults saveEventToList(EventsList list, List<Long> events) {
        Query<EventsList> querySearch = getDatastore().find(EventsList.class, "_id", list.getId());
        UpdateOperations<EventsList> updateOps = getDatastore().createUpdateOperations(EventsList.class).set("events", events);
        return getDatastore().updateFirst(querySearch, updateOps);
    }

    public UpdateResults updateName(EventsList list, String name) {
        Query<EventsList> querySearch = getDatastore().find(EventsList.class, "_id", list.getId());
        UpdateOperations<EventsList> updateOps = getDatastore().createUpdateOperations(EventsList.class).set("nombre", name);
        return getDatastore().updateFirst(querySearch, updateOps);
    }
}
