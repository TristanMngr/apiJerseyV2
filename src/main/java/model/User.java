package model;


import org.mongodb.morphia.annotations.Entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class User extends BaseMongoDO{
	private String name;
	private List<Evento> eventos;
	private List<Alarma> alarmas;
	private String password;
	private Date lastLogin;


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	// TODO change list alarm and event
	public User() {
		this.name = "TestName";
		this.setEventos(Arrays.asList(new Evento()));
		this.setAlarmas(Arrays.asList(new Alarma()));
		this.setLastLogin(new Date());
	}

	public User(String string) {
		this.name = string;
		this.setEventos(Arrays.asList(new Evento(), new Evento()));
		this.setAlarmas(Arrays.asList(new Alarma(), new Alarma()));
		this.setLastLogin(new Date());
		this.setPassword("password");
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}


	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public List<Alarma> getAlarmas() {
		return alarmas;
	}

	public void setAlarmas(List<Alarma> alarmas) {
		this.alarmas = alarmas;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
