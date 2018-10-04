package service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class EventbriteApi {
	public static String getEventByID(String eventID) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		client.register(new LoggingFilter());
		WebTarget service = client.target(getBaseURI());

		String output = "";

		try {
			Response response = service.path(eventID).queryParam("token", getAppKey())
					.request(MediaType.APPLICATION_JSON).get();
			output = response.readEntity(String.class);

		} catch (ProcessingException exception) {
			System.out.println("Explote!");
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		return output;

	}
	
	public static String getEventByName(String pattern) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		client.register(new LoggingFilter());
		WebTarget service = client.target(getBaseURI());

		String output = "";

		try {
			Response response = service.path("search").
										queryParam("q", pattern).
										queryParam("token", getAppKey())
					.request(MediaType.APPLICATION_JSON).get();
			output = response.readEntity(String.class);

		} catch (ProcessingException exception) {
			System.out.println("Explote!");
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		return output;

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("https://www.eventbriteapi.com/v3/events/").build();
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

}
