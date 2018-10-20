package model;

import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;


@Entity
public class EventsList extends BaseMongoDO {
    private Integer userId;
    private String  nombre;
    private List<Evento> eventos = new ArrayList<Evento>();     //códigos de los eventos

    public EventsList() {
    }

    public EventsList(Integer userId, String nombre) {
        this.userId = userId;
        this.nombre = nombre;
    }

    public EventsList(Integer userId, String nombre, List<Evento> eventos) {
        this.userId = userId;
        this.nombre = nombre;
        this.eventos = eventos;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

}
