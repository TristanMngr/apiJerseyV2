package main.java.com.showtime.rest;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import com.sun.jersey.core.util.StringIgnoreCaseKeyComparator;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class Event {
	private String nombre;
	private String fecha;
	private String hora;

	public Event(String nombre, String fecha, String hora) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.hora = hora;
		// System.out.println("Se creó el evento " + nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Boolean seLlama(String nombre) {
		return this.nombre.contentEquals(nombre);
	}

	

}
