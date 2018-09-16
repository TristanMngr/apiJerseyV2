package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import model.Event;

public class EventDAO {
	private final AtomicLong counter = new AtomicLong();

	// Dummy database. Initialize with some dummy values.
	private static List<Event> eventos;
	{
		eventos = new ArrayList<Event>();
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 1", "24/10/2018", "21:00"));
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 2", "12/10/2018", "21:00"));
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 3", "10/10/2018", "21:00"));
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 1", "25/10/2018", "21:00"));
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 4", "10/11/2018", "21:00"));
		this.eventos.add(new Event(counter.incrementAndGet(), "Evento 4", "30/10/2018", "21:00"));
	}

	/**
	 * Returns list of customers from dummy database.
	 * 
	 * @return list of customers
	 */
	public List<Event> list() {
		return eventos;
	}

	/**
	 * Return customer object for given id from dummy database. If customer is not
	 * found for id, returns null.
	 * 
	 * @param id customer id
	 * @return customer object for given id
	 */
	public Event get(Long id) {

		for (Event e : eventos) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Create new customer in dummy database. Updates the id and insert new customer
	 * in list.
	 * 
	 * @param customer Customer object
	 * @return customer object with updated id
	 */
	public Event create(Event evento) {
		// evento.setId(System.currentTimeMillis());
		evento.setId(counter.incrementAndGet());
		eventos.add(evento);
		return evento;
	}

	/**
	 * Delete the customer object from dummy database. If customer not found for
	 * given id, returns null.
	 * 
	 * @param id the customer id
	 * @return id of deleted customer object
	 */
	public Long delete(Long id) {

		for (Event e : eventos) {
			if (e.getId().equals(id)) {
				eventos.remove(e);
				return id;
			}
		}

		return null;
	}

	/**
	 * Update the customer object for given id in dummy database. If customer not
	 * exists, returns null
	 * 
	 * @param id
	 * @param customer
	 * @return customer object with id
	 */
	public Event update(Long id, Event evento) {

		for (Event e : eventos) {
			if (e.getId().equals(id)) {
				evento.setId(e.getId());
				eventos.remove(e);
				eventos.add(evento);
				return evento;
			}
		}

		return null;
	}

}
