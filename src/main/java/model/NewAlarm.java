package model;

import java.util.Date;

public class NewAlarm {
	private String name;
	private String codigo;
	private String nombre;
	private String categoryId;
	private Date desde;
	private Date hasta;
	
	
		
	public NewAlarm() {
		super();
	}

	public NewAlarm(String name, String codigo, String nombre, String categoryId, Date desde, Date hasta) {
		super();
		this.setName(name);
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setCategoryId(categoryId);
		this.setDesde(desde);
		this.setHasta(hasta);
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	
	
	
}
