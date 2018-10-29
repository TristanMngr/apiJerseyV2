package dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import model.Session;
import model.User;

public class SessionListDAO extends BasicDAO<Session, ObjectId> {

	public SessionListDAO(Datastore ds) {
		super(ds);
	}
	
    public Session getSessionByName(String userName) {  	
        Query<Session> query = getDatastore().find(Session.class, "userName", userName);
        Session session = query.asList().get(0);
        return session;
    }

	public List<Session>  getSessionsByUser(User user) {
		Query<Session> query = getDatastore().find(Session.class, "user", user);
		
		return query.asList();

	}
	
	public Session  getSessionByUserWithToken(User user, String token) {
		Query<Session> query = getDatastore().find(Session.class, "user", user);
		query.criteria("tokenG5").equals(token);
		
		Session session = query.asList().get(0);
		
		return session;

	}
		
}
