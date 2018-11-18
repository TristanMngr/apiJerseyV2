package dao;


import model.Alarm;
import model.Event;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.awt.*;
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

    public List<Alarm> getAlarmFromUser(User user) {
        Query<Alarm> query = getDatastore().find(Alarm.class).field("userId").hasThisOne(user.getId());
        return query.asList();
    }

    public boolean eventNotPresent(User user, Event event) {
        Query<Alarm> query = getDatastore().find(Alarm.class).
                filter("userId", user.getId()).
                filter("event.id", event.getId());
        return query.asList().isEmpty();
    }

    public Alarm getAlarmByName(User user, String name) {
        Query<Alarm> query = getDatastore().find(Alarm.class).
                filter("userId", user.getId()).
                filter("name", name);

        return query.asList().get(0);
    }
}
