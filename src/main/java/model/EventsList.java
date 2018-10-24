package model;

import eventbrite.EventBrite;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity("eventsLists")
public class EventsList extends BaseMongoDO {

    private ObjectId listaId;
    private String nombre;
    private ObjectId userId;

    @Embedded
    private List<EventBrite> events = new ArrayList<EventBrite>();

    public EventsList() {
    }

    public EventsList(String nombre) {
        this.nombre = nombre;
    }

    public EventsList(ObjectId userId, String nombre, List<EventBrite> listEvents) {
        this.userId = userId;
        this.nombre = nombre;
        this.events = listEvents;
    }

    public EventsList(List<EventBrite> listEvents) {
        this.events = listEvents;
    }

    public ObjectId  getListaId() {
        return listaId;
    }

    public void setListaId(ObjectId  listaId) {
        this.listaId = listaId;
    }

    public List<EventBrite> getEvents() {
        return events;
    }

    public void setEvents(List<EventBrite> events) {
        this.events = events;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
