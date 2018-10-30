package model;

import eventbrite.EventBrite;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity("eventsLists")
public class EventsList extends BaseMongoDO {

    private String nombre;

//    @Reference
//    private ObjectId user;

//    @Reference
    private List<Long> events = new ArrayList<Long>();
    private List<String> eventsObj = new ArrayList<String>();
    private String hexId;
    
    public EventsList(){};

    public EventsList(String user, String nombre) {
        this.nombre = nombre;
//        this.user = new ObjectId(user);
    }

    public EventsList(String user, String nombre, List<Long> listEvents) {
        this.nombre = nombre;
//        this.user = new ObjectId(user);
        this.events = listEvents;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

//    public ObjectId getUserId() {
//        return user;
//    }
//
//    public void setUserId(ObjectId user) {
//        this.user = user;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHexId() {
        return hexId;
    }

    public void setHexId(String hexId) {
        this.hexId = hexId;
    }

    public List<String> getEventsObj() {
        return eventsObj;
    }

    public void setEventsObj(List<String> eventsObj) {
        this.eventsObj = eventsObj;
    }

    
}
