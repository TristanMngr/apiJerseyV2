package main.java.service;

import java.util.List;

import main.java.dao.EventDAO;
import main.java.model.Event;

public class EventsService {

	private EventDAO eventDAO = new EventDAO();
	
	public List<Event> allEvents() {
		return this.eventDAO.list();
	}
	
	/**
	 * 
	 */
	public Event getEventById(Long id) {
		return this.eventDAO.get(id);
	}
	
}
