package com.showtime.rest;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class Event {
	private String name;
	private String fecha;
	private String hora;

	public Event(String nombre, String fecha, String hora) {
		this.name = nombre;
		this.fecha = fecha;
		this.hora = hora;
		//System.out.println("Se creó el evento " + nombre);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	

}
