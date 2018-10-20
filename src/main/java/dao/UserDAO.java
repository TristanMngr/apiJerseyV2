package dao;

import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class UserDAO extends BasicDAO<User, ObjectId> {
    //TODO to remove
    public static int counter;

    public UserDAO(Datastore datastore) {
        super(datastore);
        counter++;
    }

    public User getByUserId(ObjectId userId) {
        Query<User> query = getDatastore().find(User.class, "id", userId);
        User        user  = query.asList().get(0);
        return user;
    }

    public List<User> getAllUsers() {
        Query<User> query = getDatastore().find(User.class);
        List<User>  users = query.asList();
        return users;
    }
}
