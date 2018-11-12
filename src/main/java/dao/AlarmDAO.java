package dao;

import model.Alarm;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;

public class AlarmDAO extends BasicDAO<Alarm, ObjectId> {

    public AlarmDAO(Datastore dataStore) {
        super(dataStore);
    }

    public Key<Alarm> create(Alarm alarm) {
        return getDatastore().save(alarm);
    }
}
