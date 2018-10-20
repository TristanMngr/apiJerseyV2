package listener;

import dao.MongoDBConnection;
import model.Alarma;
import model.Evento;
import model.EventsList;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seed {
    {
        System.out.println("SEED database");

        MongoDBConnection conn      = MongoDBConnection.getInstance();
        Datastore         datastore = conn.getDatastore();

        // drop previous database
        datastore.getDB().dropDatabase();

        User userOne = new User("Christhian", "password");
        User userTwo = new User("Gille", "password");
        User userThree = new User("Francisco", "password");
        User userFour = new User("Juan", "password");
        User userFive = new User("Tristan", "password");

        Evento eventOne   = new Evento("ATLANTA'S", "5b281078cc5815bc2e5a5b2a","eventOne");
        Evento eventTwo   = new Evento("Georgetown Glow","5b281078cc5815bc2e5a5b2b","eventTwo");
        Evento eventThree = new Evento("AllStar Weekend Party", "5b281078cc5815bc2e5a5b2c","eventThree");

        Alarma alarmaOne = new Alarma("alarmaOne");
        Alarma alarmaTwo = new Alarma("alarmaTwo");
        Alarma alarmaTree = new Alarma("alarmaTree");


        EventsList eventsListOne = new EventsList("listOne", Arrays.asList(eventOne, eventTwo));
        EventsList eventListTwo = new EventsList("listTwo", Arrays.asList(eventThree, eventTwo));
        EventsList eventListThree = new EventsList("listThree", Arrays.asList(eventThree, eventTwo));
        EventsList eventListFour = new EventsList("listFour", Arrays.asList(eventOne));

        List<EventsList> eventsLists = new ArrayList<>();
        eventsLists.add(eventsListOne);
        eventsLists.add(eventListTwo);
        eventsLists.add(eventListThree);
        eventsLists.add(eventListFour);

        datastore.save(eventsLists);

        userOne.setEventos(Arrays.asList(eventListFour, eventsListOne));
        userTwo.setEventos(Arrays.asList(eventsListOne, eventListTwo));
        userThree.setEventos(Arrays.asList(eventsListOne));
        userFour.setEventos(Arrays.asList(eventListTwo));
        userFive.setEventos(Arrays.asList(eventsListOne, eventListThree));
        userFive.setEventos(Arrays.asList(eventListFour , eventListTwo));

        userOne.setAlarmas(Arrays.asList(alarmaOne, alarmaTwo, alarmaTree));

        List<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
        users.add(userFour);
        users.add(userFive);

        datastore.save(users);
    }
}
