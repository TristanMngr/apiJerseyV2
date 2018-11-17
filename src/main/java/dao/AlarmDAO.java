package dao;

import model.Alarm;
import model.EventsList;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class AlarmDAO extends BasicDAO<Alarm, ObjectId> {

    public AlarmDAO(Datastore dataStore) {
        super(dataStore);
    }

    public Key<Alarm> create(Alarm alarm) {
        return getDatastore().save(alarm);
    }

    /*public boolean isCategoryPresent(String categoryId) {
        Query<Alarm> querySearch = getDatastore().find(Alarm.class, "alarms.eventBriteList.categoryId", categoryId);
        if (querySearch.asList().get(0) == null) {
            return false;
        }
        return true;
    }*/
}
