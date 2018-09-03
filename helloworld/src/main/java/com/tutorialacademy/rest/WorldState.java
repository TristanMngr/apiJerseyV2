package com.tutorialacademy.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorldState {

	private String group;
	private String current_time;
	private String state;
	
	public WorldState() {
		System.out.println("Constructor sin parametros");
		this.state = "";
		Date current_date = new Date();
		this.current_time = current_date.toString();
	}
	
	public WorldState(String state) {
		System.out.println("Constructor con parametros");
		this.state = state;
		Date current_date = new Date();
		this.current_time = current_date.toString();
		this.group = "5";
	}
	
	 public String getCurrent_time() {
		 return current_time;
	 }

	 public String getState() {
		 return state;
	 }
	 
	public String getGroup() {
		return group;
	}

}