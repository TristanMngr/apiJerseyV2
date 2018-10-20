package model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;


@Entity("eventsLists")
public class EventsList extends BaseMongoDO {
    private String  name;

    @Embedded
    private List<Evento> eventLists = new ArrayList<Evento>();     //códigos de los eventos

    public EventsList() {
    }

    public EventsList(String name) {
        this.name = name;
    }

    public EventsList(String name, List<Evento> eventLists) {
        this.name = name;
        this.eventLists = eventLists;
    }

    public EventsList(List<Evento> eventLists) {
        this.eventLists = eventLists;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String name) {
        this.name = name;
    }

    public List<Evento> getEventLists() {
        return eventLists;
    }

    public void setEventLists(List<Evento> eventos) {
        this.eventLists = eventos;
    }

}
