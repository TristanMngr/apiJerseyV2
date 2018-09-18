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

	public List<Event> getByIdAndNombre(Long id, String nombre) {
		return this.eventDAO.getByIdAndNombre(id, nombre);
	}

}
