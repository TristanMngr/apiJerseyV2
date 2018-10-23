package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import model.Session;

public class SessionListDAO {
    private final AtomicLong counter = new AtomicLong();

    private List<Session> listadoSesiones;

    {
    	listadoSesiones = new ArrayList<Session>();
    }

	public List<Session> getListadoSesiones() {
		return listadoSesiones;
	}
    
	public void create(Session newSession) {
		int idSession = getListadoSesiones().size() + 1;
		newSession.setSessionID(idSession);
		getListadoSesiones().add(newSession);
	}
	
	
}
