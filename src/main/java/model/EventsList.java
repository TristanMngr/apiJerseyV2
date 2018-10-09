package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;
//@XmlRootElement
public class EventsList {

    private Long id;
    private Long userId;
    private String nombre;
    private List<Event> eventos = new ArrayList<Event>();

    public EventsList(Long id, Long userId, String nombre) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Event> getEventos() {
        return eventos;
    }

    public void setEventos(List<Event> eventos) {
        this.eventos = eventos;
    }

}
