package model;

import eventbrite.EventBrite;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;
//@XmlRootElement
public class EventsList {

    private Long id;
    private Integer userId;
    private String nombre;
    private List<EventBrite> eventos = new ArrayList<EventBrite>();     //códigos de los eventos

    public EventsList(Long id, Integer userId, String nombre) {
        this.id = id;
        this.userId = userId;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<EventBrite> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventBrite> eventos) {
        this.eventos = eventos;
    }

}
