package dao;


import eventbrite.EventBrite;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

public class EventBriteDAO  extends BasicDAO<EventBrite, ObjectId> {
    public EventBriteDAO(Datastore dataStore) {
        super(dataStore);
    }

    public boolean isCategoryIdPresent(String categoryId) {
        Query<EventBrite> query = getDatastore().find(EventBrite.class).
                field("categoryId").exists().
                field("categoryId").hasThisOne(categoryId);
        return !query.asList().isEmpty();
    }
}
