package listener;

import dao.MongoDBConnection;
import model.Evento;
import model.EventsList;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seed {
    {
        System.out.println("SEED database");

        MongoDBConnection conn      = MongoDBConnection.getInstance();
        Datastore         datastore = conn.getDatastore();

        Evento eventOne   = new Evento("eventOne");
        Evento eventTwo   = new Evento("eventTwo");
        Evento eventThree = new Evento("eventThree");

        EventsList eventListeOne   = new EventsList(1, "Lista 1", Arrays.asList(eventOne, eventTwo));
        EventsList eventListeTwo   = new EventsList(1, "Lista 2", Arrays.asList(eventThree, eventTwo));
        EventsList eventListeThree = new EventsList(2, "Lista 3", Arrays.asList(eventOne));
        EventsList eventListeFour  = new EventsList(2, "Lista 4", Arrays.asList(eventOne, eventTwo, eventThree));
        EventsList eventListeFive  = new EventsList(1, "Lista 5", Arrays.asList());


        List listas = new ArrayList<EventsList>();
        listas.add(eventListeOne);
        listas.add(eventListeTwo);
        listas.add(eventListeThree);
        listas.add(eventListeFour);
        listas.add(eventListeFive);

        datastore.save(listas);
    }
}
