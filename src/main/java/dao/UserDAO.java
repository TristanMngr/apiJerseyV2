package dao;

import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;
import model.EventsList;
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
    	
        Query<User> query = getDatastore().find(User.class, "userName", userName);
        List<User> listado = query.asList();
        if(listado != null && !listado.isEmpty())
        	return listado.get(0);
        return null;
    }

    public List<User> getAllUsers() {
        Query<User> query = getDatastore().find(User.class);
        List<User> users = query.asList();
        return users;
    }

    public UpdateResults saveEventsListsToUser(ObjectId userId, List<EventsList> lists) {
        Query<User> querySearch = getDatastore().find(User.class, "id", userId);
        UpdateOperations<User> updateOps = getDatastore().createUpdateOperations(User.class).set("eventsLists", lists);
        return getDatastore().updateFirst(querySearch, updateOps);
    }
}
