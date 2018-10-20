package model;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Evento extends BaseMongoDO {
    private String name;

    public Evento() {
    }

    public Evento(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
