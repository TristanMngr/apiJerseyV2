package model;

import eventbrite.EventBrite;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Reference;

@Entity("eventsLists")
public class EventsList extends BaseMongoDO {

    private String nombre;

    @Reference
    private String user;

    @Reference
    private List<Long> events = new ArrayList<Long>();

    public EventsList() {
    }

    public EventsList(String user, String nombre) {
        this.nombre = nombre;
        this.user = user;
    }

    public EventsList(String user, String nombre, List<Long> listEvents) {
        this.nombre = nombre;
        this.user = user;
        this.events = listEvents;
    }

    public EventsList(List<Long> listEvents) {
        this.events = listEvents;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    public String getUserId() {
        return user;
    }

    public void setUserId(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
