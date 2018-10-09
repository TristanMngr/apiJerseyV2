package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;

import model.EventsList;

public class EventsListDAO {

    private final AtomicLong counter = new AtomicLong();

    // Dummy database. Initialize with some dummy values.
    private static List<EventsList> listas;

    {
        listas = new ArrayList<EventsList>();
        this.listas.add(new EventsList(counter.incrementAndGet(), 1L, "Lista 1"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 1L, "Lista 2"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 2L, "Lista 3"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 2L, "Lista 4"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 1L, "Lista 5"));
    }

    /**
     * Returns list of eventslists from dummy database.
     *
     * @return list of eventslists
     */
    public List<EventsList> getAllLists() {
        return listas;
    }

    /**
     * 
     * @param id
     * @return
     */
    public List<EventsList> getByUserId(Long userId) {
//        List<EventsList> results = new ArrayList<EventsList>();
        return listas.stream().filter(elem->elem.getUserId()==userId).collect(Collectors.toList());
//        for (EventsList e : listas) {
//            if (e.getUserId().equals(userId) ) {
//                results.add(e);
//            }
//        }
//        return results;
    }

    /**
     * Return EventsList object for given id from dummy database. If EventsList
     * is not found for id, returns null.
     *
     * @param id EventsList id
     * @return EventsList object for given id
     */
    public EventsList get(Long id) {

        for (EventsList e : listas) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     *
     */
    public List<EventsList> getByIdAndNombre(Long id, String nombre) {
        List<EventsList> results = new ArrayList<EventsList>();
        for (EventsList e : listas) {
            if (e.getId().equals(id) && e.getNombre().equals(nombre)) {
                results.add(e);
            }
        }
        return results;
    }

    /**
     * Create new EventsList in dummy database. Updates the id and insert new
     * EventsList in list.
     *
     * @param lista EventsList object
     * @return lista object with updated id
     */
    public EventsList create(EventsList lista) {
        // lista.setId(System.currentTimeMillis());
        lista.setId(counter.incrementAndGet());
        listas.add(lista);
        return lista;
    }

    /**
     * Delete the EventsList object from dummy database. If EventsList not found
     * for given id, returns null.
     *
     * @param id the EventsList id
     * @return id of deleted EventsList object
     */
    public Long delete(Long id) {

        for (EventsList e : listas) {
            if (e.getId().equals(id)) {
                listas.remove(e);
                return id;
            }
        }

        return null;
    }

    /**
     * Update the EventsList object for given id in dummy database. If
     * EventsList not exists, returns null
     *
     * @param id
     * @param lista
     * @return EventsList object with id
     */
    public EventsList update(Long id, EventsList lista) {

        for (EventsList l : listas) {
            if (l.getId().equals(id)) {
                lista.setId(l.getId());
                listas.remove(l);
                listas.add(lista);
                return lista;
            }
        }

        return null;
    }

    public Boolean addEventToList(EventsList lista, Long eventoId) {

        return true;
    }

}
