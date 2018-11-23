package dao;

import eventbrite.EventBrite;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.Date;
import java.util.List;
import model.EventsList;
import model.NewAlarm;

import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

public class UserDAO extends BasicDAO<User, ObjectId> {

    public UserDAO(Datastore datastore) {
        super(datastore);
    }

    public User getByUserId(ObjectId userId) {
        Query<User> query = getDatastore().find(User.class, "id", userId);
        User user = query.asList().get(0);
        return user;
    }

    public User getUserByName(String userName) {
    	System.out.println(this.getClass().getName() + ":: getUserByName");
        Query<User> query = getDatastore().find(User.class).
                field("userName").exists().
                field("userName").hasThisOne(userName);

        /*for (User user : query.asList()) {
            System.out.println(user.getUserName());
        }*/

        List<User> listado = query.asList();


        
        if (listado != null && !listado.isEmpty()) {
        	return listado.get(0);
        }
        return null;
    }

    public List<User> getAllUsers() {
        Query<User> query = getDatastore().find(User.class);
        List<User> users = query.asList();
        return users;
    }
    
    public List<User> getAllNonAdminUsers() {
    	Query<User> query = getDatastore().createQuery(User.class).field("role").notEqual("ADMIN");
        List<User> users = query.asList();
        return users;
    }

    public UpdateResults saveEventsListsToUser(ObjectId userId, List<EventsList> lists) {
        Query<User> querySearch = getDatastore().find(User.class, "id", userId);
        UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).set("eventsLists", lists);
        return getDatastore().updateFirst(querySearch, updateOps);
    }

    public UpdateResults removeEventsListFromUser(ObjectId userId, EventsList list) {
        Query<User> querySearch = getDatastore().find(User.class, "id", userId);
        UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).removeAll("eventsLists", list);
        return getDatastore().update(querySearch, updateOps);
    }

    /*public UpdateResults updateAlarms(ObjectId userId, List<EventBrite> eventBriteList) {
        Query<User> querySearch = getDatastore().find(User.class, "id", userId);
        UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).set("alarms.", eventBriteList);
    }*/

	public void updateLastLogin(String username) {
		System.out.println(this.getClass().getName() + "::updateLastLogin");
		Query<User> query = getDatastore().find(User.class, "userName", username);
		UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).set("lastLogin", new Date());
		getDatastore().update(query, updateOps);
		return;
	}

	public void saveAlarm(String username, NewAlarm alarm) {
		// TODO: check that we are not saving the same alarm's name twice.
		
		Query<User> query = getDatastore().createQuery(User.class).field("userName").equal(username);
        List<User> users = query.asList();
		User actualUser = users.get(0);
		List<NewAlarm> alarmas = actualUser.getNuevasAlarmas();
		alarmas.add(alarm);
		
		UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).set("nuevasAlarmas", alarmas);
		getDatastore().update(query, updateOps);
		return;
		
	}
}
