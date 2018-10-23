package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;


@Entity("eventsLists")
public class EventsList extends BaseMongoDO {
    private String  nombre;

    private ObjectId userId;

    @Embedded
    private List<Evento> listEvents = new ArrayList<Evento>();     //códigos de los listEvents

    public EventsList() {
    }

    public EventsList(String nombre) {
        this.nombre = nombre;
    }

    public EventsList(ObjectId userId, String nombre, List<Evento> listEvents) {
        this.userId = userId;
        this.nombre = nombre;
        this.listEvents = listEvents;
    }

    public EventsList(List<Evento> listEvents) {
        this.listEvents = listEvents;
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

    public List<Evento> getEventos() {
        return listEvents;
    }

    public void setEventos(List<Evento> listEvents) {
        this.listEvents = listEvents;
    }

}
