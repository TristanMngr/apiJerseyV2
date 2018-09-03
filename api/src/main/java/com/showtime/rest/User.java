package com.showtime.rest;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class User {
	private String name;
	private List<String> eventos;
	private List<String> alarmas;
	private Date lastLogin;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public User() {
		this.name = "TestName";
		this.setEventos(Arrays.asList(""));
		this.setAlarmas(Arrays.asList(""));
		this.setLastLogin(new Date());
	}

	public User(String string) {
		this.name = string;
		this.setEventos(Arrays.asList(""));
		this.setAlarmas(Arrays.asList(""));
		this.setLastLogin(new Date());
	}

	public List<String> getEventos() {
		return eventos;
	}

	public void setEventos(List<String> eventos) {
		this.eventos = eventos;
	}


	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public List<String> getAlarmas() {
		return alarmas;
	}

	public void setAlarmas(List<String> alarmas) {
		this.alarmas = alarmas;
	}
	
}
