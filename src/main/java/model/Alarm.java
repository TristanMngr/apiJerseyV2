package model;

import eventbrite.EventBrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alarm extends BaseMongoDO {
    private String name;

    private Map<String, String> paramsEventBrite = new HashMap<>();

    private List<EventBrite> eventBriteList = new ArrayList<>();

    public Alarm() {
    }

    // TODO to remove
    public Alarm(String name, String categoryId) {
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

    public List<EventBrite> getEventBriteList() {
        return eventBriteList;
    }

    public void setEventBriteList(List<EventBrite> eventBriteList) {
        this.eventBriteList = eventBriteList;
    }
}
