package dao;

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

	public Key<Event> saveEvent(Long codigoEvento) {
    	Query<Event> query = getDatastore().createQuery(Event.class).field("eventBriteID").equal(codigoEvento);  	
		List<Event> listado = query.asList();
        if(listado != null && !listado.isEmpty())
        	return null;
		
        return getDatastore().save(new Event(codigoEvento));
	}

	public int getEventsCount(int days) {
		
		Date today = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, days * -1);
		Date from = cal.getTime();
		
		Query<Event> query;
		if (days != -1)
			query = getDatastore().createQuery(Event.class).field("created").greaterThan(from);
		else
			query = getDatastore().createQuery(Event.class);
		
		List<Event> listado = query.asList();
    	if(listado != null && !listado.isEmpty())
        	return listado.size();
    	
    	return 0;
		
	}

}
