package listener;

import dao.MongoDBConnection;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Seed {

    {
        System.out.println("SEED database");

        MongoDBConnection conn = MongoDBConnection.getInstance();
        Datastore datastore = conn.getDatastore();

        datastore.getDB().dropDatabase();
        
        String userOneId="5bcbba1743b244dd134d6f44";
        String userTwoId="5bcbba1743b244dd134d6f45";
        String userThreeId="5bcbba1743b244dd134d6f46";
        String userFourId="5bcbba1743b244dd134d6f47";
        String userFiveId="5bcbba1743b244dd134d6f48";

        String password = "password";

        User userOne = new User(new ObjectId(userOneId), "Christhian", new String(Base64.getEncoder().encode(("Christian:" + password).getBytes())));
        User userTwo = new User(new ObjectId(userTwoId), "Guille", new String(Base64.getEncoder().encode(("Guille:" + password).getBytes())));
        User userThree = new User(new ObjectId(userThreeId), "Francisco", new String(Base64.getEncoder().encode(("Francisco:" + password).getBytes())));
        User userFour = new User(new ObjectId(userFourId), "Juan", new String(Base64.getEncoder().encode(("Juan:" + password).getBytes())));
        User userFive = new User(new ObjectId(userFiveId), "Tristan", new String(Base64.getEncoder().encode(("Tristan:" + password).getBytes())));

//        Evento eventOne   = new Evento("ATLANTA'S", "5b281078cc5815bc2e5a5b2a","eventOne");
//        Evento eventTwo   = new Evento("Georgetown Glow","5b281078cc5815bc2e5a5b2b","eventTwo");
//        Evento eventThree = new Evento("AllStar Weekend Party", "5b281078cc5815bc2e5a5b2c","eventThree");
        /*Alarm alarmOne  = new Alarm("alarmOne");
        Alarm alarmTwo  = new Alarm("alarmTwo");
        Alarm alarmTree = new Alarm("alarmTree");

        EventsList eventsListOne = new EventsList(userOneId,"listOne", Arrays.asList());
        EventsList eventListTwo = new EventsList(userOneId, "listTwo", Arrays.asList());
        EventsList eventListThree = new EventsList(userTwoId, "listThree", Arrays.asList());
        EventsList eventListFour = new EventsList(userTwoId, "listFour", Arrays.asList());
        EventsList eventsListFive = new EventsList(userThreeId,"listOne", Arrays.asList());
        EventsList eventsListSix = new EventsList(userFourId,"listOne", Arrays.asList());
        EventsList eventsListSeven = new EventsList(userFiveId,"listOne", Arrays.asList());
        EventsList eventsListEight = new EventsList(userFiveId,"listOne", Arrays.asList());

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

        userOne.setAlarms(Arrays.asList(alarmOne, alarmTwo, alarmTree));*/

        List<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
        users.add(userFour);
        users.add(userFive);

        datastore.save(users);
    }
}
