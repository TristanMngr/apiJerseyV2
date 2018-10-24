package model;

public class Evento {
    private String   name;
    private String codigo;
    private String   category;

    public Evento() {
    }

    public Evento(String name) {
        this.name = name;
    }

    public Evento(String name, String codigo, String category) {
        this.name = name;
        this.codigo = codigo;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
