package service;

import dao.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase para funciones genéricas que se usen en varios lugares
 */
public class ManagementService {

    public static EventsListDAO eventsListsDAO;
    public static UserDAO userDAO;
    public static SessionListDAO listadoSesiones;
    public static AlarmDAO alarmDAO;
	private static EventsDAO listadoEventos;

    public static void createDAOs() {
    	System.out.println("ManagementService::createDAOs");
    	MongoDBConnection conn = MongoDBConnection.getInstance();

        userDAO = new UserDAO(conn.getDatastore());
        eventsListsDAO = new EventsListDAO(conn.getDatastore(), userDAO);
        listadoSesiones = new SessionListDAO(conn.getDatastore());
        listadoEventos = new EventsDAO(conn.getDatastore());
        alarmDAO = new AlarmDAO(conn.getDatastore());
    }

    public static SessionListDAO getSessionListDAO() {
        return listadoSesiones;
    }

    public static EventsListDAO getEventsListDAO() {
        return eventsListsDAO;
    }

    public static AlarmDAO getAlarmDAO() {
        return alarmDAO;
    }
    
	public static EventsDAO getEventsDAO() {
		return listadoEventos;
	}

    public static UserDAO getUserDAO() {
    	System.out.println("ManagementService::getUserDAO");
    	
    	if(userDAO == null)
    		System.out.println("ManagementService::getUserDAO will return null");
    	 	
        return userDAO;
    }

    public static Map<String, String> getPostParams(String params) {
        List<String> listaAux = Arrays.asList(params.split("&", -1));
        Map<String, String> parametros = new HashMap<String, String>();
        listaAux.stream().map(elem -> parametros.put(getNValueFromSplit(elem, "=", 0), getNValueFromSplit(elem, "=", 1))).collect(Collectors.toList());
        System.out.println(parametros);
        return parametros;
    }

    public static String getNValueFromSplit(String sentencia, String separador, Integer posicion) {
        List<String> lista = Arrays.asList(sentencia.split(separador, -1));
        return lista.get(posicion);
    }



}
