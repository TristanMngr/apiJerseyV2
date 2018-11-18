package model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alarm extends BaseMongoDO {
    private String name;

    private Map<String, String> paramsEventBrite = new HashMap<>();

    private List<Event> event = new ArrayList<>();

    private ObjectId userId;

    public Alarm() {
    }

    // TODO to remove
    public Alarm(ObjectId userId, String name, String categoryId) {
        this.userId = userId;
        this.name = name;
        paramsEventBrite.put("categoryId", categoryId);
    }

    public Alarm(String name) {
        this.name = name;
    }

    public Map<String, String> getParamsEventBrite() {
        return paramsEventBrite;
    }

    public void setParamsEventBrite(Map<String, String> paramsEventBrite) {
        this.paramsEventBrite = paramsEventBrite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
}
