package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import model.Alarm;
import model.User;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.json.JSONArray;
import org.json.JSONObject;

//EVENTBRITE KEY: Q2U3MHJELN4VOBCARDUQ - NO BORRAR QUE LA NECESITO PARA BUSCAR DESDE LA WEB DE EVENTBRITE (GUILLE)
public class EventbriteService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String searchEvents(Map<String, String> paramsEventBrite) {
        String eventosEncontrados;
        //TODO: patrón strategy pendiente
        if (!paramsEventBrite.get("codigo").equals("")) {
            eventosEncontrados = formatJsonEventoSimple(getJsonEventByID(paramsEventBrite.get("codigo")));
        } else {
            eventosEncontrados = getEventsByParams(paramsEventBrite);
        }
        return eventosEncontrados;
    }

    public static String getEventsByParams(Map<String, String> paramsEventBrite) {
        WebTarget service = getWebTargetService("events/search");
        Response response = service
                .queryParam("q", paramsEventBrite.get("nombre"))
                .queryParam("categories", paramsEventBrite.get("categoryId"))
                .queryParam("start_date.range_start", completeStringDatetime(paramsEventBrite.get("fechaDesde")))
                .queryParam("start_date.range_end", completeStringDatetime(paramsEventBrite.get("fechaHasta")))
                .queryParam("token", getAppKey()).request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(String.class);
    }

    /**
     * busca un evento en la API de eventbrite
     *
     * @param eventID
     * @return
     */
    public static String getJsonEventByID(String eventID) {
        WebTarget service  = getWebTargetService("events");
        Response  response = service.path(eventID).queryParam("token", getAppKey()).request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 404) {
            return response.readEntity(String.class);
        } else {
            return "";
        }
    }

    public static EventBrite getEventByID(Long codigoEvento) throws JsonProcessingException, IOException {
        String eventoJson = getJsonEventByID(codigoEvento.toString());
        return mapper.readValue(eventoJson, EventBrite.class);
    }

    /* ****************** Categorías ************************* */
    public static String getAllCategories() {
        WebTarget service  = getWebTargetService("categories");
        Response  response = service.queryParam("token", getAppKey()).request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(String.class);
    }

    /* *********************** métodos auxiliares ********************** */

    /**
     * formatea el json agregando: {"pagination": {"object_count": 18,
     * "page_number": 1, "page_size": 50, "page_count": 1, "has_more_items":
     * false}, "events": []}; sirve para mantener el mismo formato que cuando se
     * busca por otros parámetros
     *
     * @param eventos
     * @return eventosFormateado
     */
    private static String formatJsonEventoSimple(String eventos) {
        return "{\"pagination\": {\"object_count\": 1,\"page_number\": 1,\"page_size\": 50,\"page_count\": 1,\"has_more_items\":false}, \"events\":[" + eventos + "]}";
    }

    private static WebTarget getWebTargetService(String baseElement) {
        ClientConfig config = new ClientConfig();
        Client       client = ClientBuilder.newClient(config);
        client.register(new LoggingFilter());
        WebTarget service = client.target(getBaseURI(baseElement));
        return service;
    }

    private static URI getBaseURI(String baseElement) {
        return UriBuilder.fromUri("https://www.eventbriteapi.com/v3/" + baseElement + "/").build();
    }

    private static String getAppKey() {
        return EncryptionServices.decrypt("YVOWlcdl36bCvwcaUy8QWbdEQ8cyGKorBRAd3I5dinM=");
    }

    private static String completeStringDatetime(String date) {
        if (!date.equals("")) {
            return date + "T00:00:00";
        } else {
            return "";
        }
    }

    public static List<EventBrite> getEventsSinceLastConnexion(User user) throws IOException {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String events = "";
        List<EventBrite> eventBriteList = new ArrayList<>();

        if (Utils.getDateDiff(user.getLastLogin(), today.getTime(), TimeUnit.DAYS) >= 1) {
            Map<String, String> paramsEventBrite = new HashMap<>();
            for (Alarm alarm : user.getAlarms()) {
                for (String key : alarm.getParamsEventBrite().keySet()) {
                    paramsEventBrite.put(key, alarm.getParamsEventBrite().get(key));
                }
                paramsEventBrite.put("fechaDesde", df.format(user.getLastLogin()));
                paramsEventBrite.put("fechaHasta", "");
                events += getEventsByParams(paramsEventBrite);
                paramsEventBrite = new HashMap<>();
                eventBriteList = stringToEventBriteObjectList(events);
            }
        }

        return eventBriteList;
    }

    public static List<EventBrite> stringToEventBriteObjectList(String events) throws IOException {
        List<EventBrite> eventBriteList = new ArrayList<>();

        JSONObject jsonObj = new JSONObject(events);
        JSONArray jsonEvents = (JSONArray) jsonObj.get("events");

        for (Object event : jsonEvents) {
            EventBrite eventBrite = mapper.readValue(event.toString(), EventBrite.class);
            System.out.println(eventBrite.getId());
            eventBriteList.add(eventBrite);
        }

        return eventBriteList;
    }
}
