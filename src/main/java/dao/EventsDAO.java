package dao;

import com.mongodb.WriteResult;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import model.Event;

public class EventsDAO extends BasicDAO<Event, ObjectId> {

    public EventsDAO(Datastore ds) {
        super(ds);
    }

    public Key<Event> saveEvent(Event event) {
//        Query<Event> query = getDatastore().createQuery(Event.class).field("eventBriteID").equal(event.getEventBriteID());
//        List<Event> listado = query.asList();
//        if (listado != null && !listado.isEmpty()) {
//            return null;
//        }

        return getDatastore().save(event);
    }

    public int getEventsCount(int days) {

        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, days * -1);
        Date from = cal.getTime();

        Query<Event> query;
        if (days != -1) {
            query = getDatastore().createQuery(Event.class).field("created").greaterThan(from);
        } else {
            query = getDatastore().createQuery(Event.class);
        }

        List<Event> listado = query.asList();
        if (listado != null && !listado.isEmpty()) {
            return listado.size();
        }

        return 0;

    }

    public WriteResult deleteEventsByListaId(ObjectId listaId) {
        Query<Event> querySearch = getDatastore().find(Event.class, "eventsList", listaId);
        WriteResult writeResult = getDatastore().delete(querySearch);
        return writeResult;
    }

}
