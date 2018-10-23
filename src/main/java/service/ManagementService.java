package service;

import dao.EventsListDAO;
import dao.SessionListDAO;
import dao.UsersListDAO;
import model.Session;

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
    public static UsersListDAO usersListDAO;
    public static SessionListDAO listadoSesiones;

    public static void createDAOs() {
        eventsListsDAO = new EventsListDAO();
        usersListDAO = new UsersListDAO();
        listadoSesiones = new SessionListDAO();
    }

    public static SessionListDAO getSessionListDAO() {
        return listadoSesiones;
    }
    
    public static EventsListDAO getEventsListDAO() {
        return eventsListsDAO;
    }
    
    public static UsersListDAO getUsersListDAO() {
        return usersListDAO;
    }

    public static Map<String, String> getPostParams(String params) {
        List<String> listaAux = Arrays.asList(params.split("&", -1));
        Map<String, String> parametros = new HashMap<String, String>();
        listaAux.stream().map(elem -> parametros.put(getNValueFromSplit(elem, "=", 0), getNValueFromSplit(elem, "=", 1))).collect(Collectors.toList());
        return parametros;
    }

    public static String getNValueFromSplit(String sentencia, String separador, Integer posicion) {
        List<String> lista = Arrays.asList(sentencia.split(separador, -1));
        return lista.get(posicion);
    }

}
