package model;

public class EventBriteLight {
    private String id;
    private String name;
    private String start;
    private String end;
    private String categoryId;

    public EventBriteLight() {
    }

    public EventBriteLight(String id, String name, String start, String end, String categoryId) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
