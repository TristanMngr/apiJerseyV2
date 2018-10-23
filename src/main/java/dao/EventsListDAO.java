package dao;

import eventbrite.EventBrite;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;

import model.EventsList;

public class EventsListDAO {

    private final AtomicLong counter = new AtomicLong();

    // Dummy database. Initialize with some dummy values.
    private List<EventsList> listas;

    {
//        System.out.println("arma la lista de listas");
        listas = new ArrayList<EventsList>();
        this.listas.add(new EventsList(counter.incrementAndGet(), 1, "Lista 1"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 1, "Lista 2"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 2, "Lista 3"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 2, "Lista 4"));
        this.listas.add(new EventsList(counter.incrementAndGet(), 1, "Lista 5"));
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
    public List<EventsList> getByUserId(Integer userId) {
        List<EventsList> results = new ArrayList<EventsList>();
        results = listas.stream().filter(elem -> elem.getUserId() == userId).collect(Collectors.toList());

//        for (EventsList e : results) {
//            System.out.println(e.getNombre()+" - ");
//        }
        return results;
    }

    public Boolean addEventToList(EventsList lista, EventBrite evento) {
        return lista.getEventos().add(evento);
    }

    /**
     * Create new EventsList in dummy database. Updates the id and insert new
     * EventsList in list.
     *
     * @param lista EventsList object
     * @return lista object with updated id
     */
    public Long create(String nombre, Integer userId) {
        EventsList nuevaLista=new EventsList(counter.incrementAndGet(), userId, nombre);
        if(listas.add(nuevaLista)){
            return nuevaLista.getId();
        }else{
            return 0L;
        }
        
    }

    /**
     * Return EventsList object for given id from dummy database. If EventsList
     * is not found for id, returns null.
     *
     * @param id EventsList id
     * @return EventsList object for given id
     */
    public EventsList getById(Long id) {
        return listas.stream().filter(elem -> elem.getId().equals(id)).collect(Collectors.toList()).get(0);
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

}
