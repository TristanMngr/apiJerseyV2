package com.mysticalpotato.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coche {

	String marca;
	String modelo;
	long potencia;

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public long getPotencia() {
		return potencia;
	}

	public void setPotencia(long potencia) {
		this.potencia = potencia;
	}
}