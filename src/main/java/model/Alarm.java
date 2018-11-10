package model;

import java.util.HashMap;
import java.util.Map;

public class Alarm extends BaseMongoDO  {
    private String name;

    private Map<String, String> paramsEventBrite;

    public Alarm() {
    }

    // TODO to remove
    public Alarm(String name, String categoryId) {
        setName(name);
        paramsEventBrite = new HashMap<>();
        paramsEventBrite.put("categoryId", categoryId);

    }

    public Map<String, String> getParamsEventBrite() {
        return paramsEventBrite;
    }

    public void setParamsEventBrite(Map<String, String> paramsEventBrite) {
        this.paramsEventBrite = paramsEventBrite;
    }

    public Alarm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
