package model;

import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Reference;

@Entity("eventsLists")
public class EventsList extends BaseMongoDO {

    private String nombre;

//    @Reference
    private ObjectId user;

//    @Reference
    private List<Long> events = new ArrayList<Long>();
    private List<String> eventsObj = new ArrayList<String>();
    private String hexId;
    
    public EventsList(){}

    public EventsList(ObjectId userId, String nombre) {
        this.nombre = nombre;
        this.user = userId;
    }

    public EventsList(ObjectId userId, String nombre, List<Long> listEvents) {
        this.nombre = nombre;
        this.user = userId;
        this.events = listEvents;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }
    
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
