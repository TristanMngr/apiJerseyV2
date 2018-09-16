package model;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String name;
	private List<String> eventos;
	private List<String> alarmas;
	private String password;
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
		this.setPassword("password");
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
