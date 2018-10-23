package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventbrite.EventBrite;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

//import com.google.gson.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

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
        WebTarget service = getWebTargetService("events");
        Response response = service.path(eventID).queryParam("token", getAppKey()).request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 404) {
            return response.readEntity(String.class);
        } else {
            return "";
        }
    }

    public static EventBrite getEventByID(Long codigoEvento) throws JsonProcessingException,IOException {
        String eventoJson = getJsonEventByID(codigoEvento.toString());
        return mapper.readValue(eventoJson, EventBrite.class);
    }

    /* ****************** Categorías ************************* */
    public static String getAllCategories() {
        WebTarget service = getWebTargetService("categories");
        Response response = service.queryParam("token", getAppKey()).request(MediaType.APPLICATION_JSON).get();
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
        Client client = ClientBuilder.newClient(config);
        client.register(new LoggingFilter());
        WebTarget service = client.target(getBaseURI(baseElement));
        return service;
    }

    private static URI getBaseURI(String baseElement) {
        return UriBuilder.fromUri("https://www.eventbriteapi.com/v3/" + baseElement + "/").build();
    }

//	  private static String getAppKey() {
//		    return "Q2U3MHJELN4VOBCARDUQ";
//	  }
    private static String getAppKey() {
        SecretKeySpec secretKey = null;
        byte[] key;
        String strToDecrypt = "YVOWlcdl36bCvwcaUy8QWbdEQ8cyGKorBRAd3I5dinM=";

        String myKey = System.getenv("EVENTBRITE_KEY");
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Decrypt
        Cipher cipher;
        String decryptedString = "";
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedString;
    }

    private static String completeStringDatetime(String date) {
        if (!date.equals("")) {
            return date + "T00:00:00";
        } else {
            return "";
        }

    }

}
