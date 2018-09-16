package service;

import java.util.List;

import dao.EventDAO;
import model.Event;


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
