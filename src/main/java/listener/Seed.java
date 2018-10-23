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


        User userOne = new User(new ObjectId("5bcbba1743b244dd134d6f44"), "Christhian", "password");
        User userTwo = new User(new ObjectId("5bcbba1743b244dd134d6f45"), "Gille", "password");
        User userThree = new User(new ObjectId("5bcbba1743b244dd134d6f46"), "Francisco", "password");
        User userFour = new User(new ObjectId("5bcbba1743b244dd134d6f47"), "Juan", "password");
        User userFive = new User(new ObjectId("5bcbba1743b244dd134d6f48"),"Tristan", "password");

        Evento eventOne   = new Evento("ATLANTA'S", "5b281078cc5815bc2e5a5b2a","eventOne");
        Evento eventTwo   = new Evento("Georgetown Glow","5b281078cc5815bc2e5a5b2b","eventTwo");
        Evento eventThree = new Evento("AllStar Weekend Party", "5b281078cc5815bc2e5a5b2c","eventThree");

        Alarma alarmaOne = new Alarma("alarmaOne");
        Alarma alarmaTwo = new Alarma("alarmaTwo");
        Alarma alarmaTree = new Alarma("alarmaTree");


        EventsList eventsListOne = new EventsList(new ObjectId("5bcbba1743b244dd134d6f44"),"listOne", Arrays.asList(eventOne, eventTwo));
        EventsList eventListTwo = new EventsList(new ObjectId("5bcbba1743b244dd134d6f44"), "listTwo", Arrays.asList(eventThree, eventTwo));
        EventsList eventListThree = new EventsList(new ObjectId("5bcbba1743b244dd134d6f45"), "listThree", Arrays.asList(eventThree, eventTwo));
        EventsList eventListFour = new EventsList(new ObjectId("5bcbba1743b244dd134d6f45"), "listFour", Arrays.asList(eventOne));
        EventsList eventsListFive = new EventsList(new ObjectId("5bcbba1743b244dd134d6f46"),"listOne", Arrays.asList(eventOne, eventTwo));
        EventsList eventsListSix = new EventsList(new ObjectId("5bcbba1743b244dd134d6f47"),"listOne", Arrays.asList(eventOne, eventTwo));
        EventsList eventsListSeven = new EventsList(new ObjectId("5bcbba1743b244dd134d6f48"),"listOne", Arrays.asList(eventOne, eventTwo));
        EventsList eventsListEight = new EventsList(new ObjectId("5bcbba1743b244dd134d6f48"),"listOne", Arrays.asList(eventOne, eventTwo));


        List<EventsList> eventsLists = new ArrayList<>();
        eventsLists.add(eventsListOne);
        eventsLists.add(eventListTwo);
        eventsLists.add(eventListThree);
        eventsLists.add(eventListFour);
        eventsLists.add(eventsListFive);
        eventsLists.add(eventsListSix);
        eventsLists.add(eventsListSeven);
        eventsLists.add(eventsListEight);

        datastore.save(eventsLists);

        userOne.setEventos(Arrays.asList(eventsListOne, eventListTwo));
        userTwo.setEventos(Arrays.asList(eventListThree, eventListFour));
        userThree.setEventos(Arrays.asList(eventsListFive));
        userFour.setEventos(Arrays.asList(eventsListSix));
        userFive.setEventos(Arrays.asList(eventsListSeven, eventsListEight));

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
