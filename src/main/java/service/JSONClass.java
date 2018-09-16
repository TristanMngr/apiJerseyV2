package service;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JSONClass {

	private String stringElement;
	private int intElement;
	private List<String> listElement;
	
	public JSONClass() {
		setStringElement("");
		setIntElement(0);
		setListElement(null);
	}
	
	public JSONClass(String string, int i, List<String> asList) {
		setStringElement(string);
		setIntElement(i);
		setListElement(asList);
	}

	public String getStringElement() {
		return stringElement;
	}

	public void setStringElement(String stringElement) {
		this.stringElement = stringElement;
	}

	public int getIntElement() {
		return intElement;
	}

	public void setIntElement(int intElement) {
		this.intElement = intElement;
	}

	public List<String> getListElement() {
		return listElement;
	}

	public void setListElement(List<String> listElement) {
		this.listElement = listElement;
	}

}
