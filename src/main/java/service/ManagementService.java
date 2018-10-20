package service;

import dao.EventsListDAO;
import dao.MongoDBConnection;
import org.mongodb.morphia.Datastore;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase para funciones genéricas que se usen en varios lugares
 */
public class ManagementService {
    public static EventsListDAO eventsListsDAO;

    public static void createDAOs() {
        MongoDBConnection conn = MongoDBConnection.getInstance();
        eventsListsDAO = new EventsListDAO(conn.getDatastore());
    }

    public static EventsListDAO getEventsListDAO() {
        return eventsListsDAO;
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
