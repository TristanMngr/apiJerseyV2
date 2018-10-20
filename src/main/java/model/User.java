package model;


import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Entity("users")
public class User extends BaseMongoDO{
	private String name;

	@Reference
	private List<EventsList> eventsLists;

	@Embedded
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
		this.setAlarmas(Arrays.asList(new Alarma()));
		this.setLastLogin(new Date());
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User(String string) {
		this.name = string;
		this.setAlarmas(Arrays.asList(new Alarma(), new Alarma()));
		this.setLastLogin(new Date());
		this.setPassword("password");
	}

	public List<EventsList> getEventsLists() {
		return eventsLists;
	}

	public void setEventos(List<EventsList> eventsLists) {
		this.eventsLists = eventsLists;
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
