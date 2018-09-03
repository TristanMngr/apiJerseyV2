package com.tutorialacademy.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
	}

	public User(String string) {
		this.name = string;
		this.setEventos(Arrays.asList(""));
	}

	public List<String> getEventos() {
		return eventos;
	}

	public void setEventos(List<String> eventos) {
		this.eventos = eventos;
	}

	public List<String> getAlarmas() {
		return alarmas;
	}


	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
}
