package com.showtime.rest;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class EventsList {
	private String nombre;
	private List<Event> eventos;

	public EventsList(String nombre) {
		this.nombre = nombre;
		//System.out.println("Se creó la lista " + nombre);
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
