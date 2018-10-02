package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import dao.EventDAO;
import eventbrite.EventBrite;
import model.Event;

public class EventsService {

	private static EventDAO eventDAO = new EventDAO();

	public List<Event> allEvents() {
		return this.eventDAO.list();
	}

	/**
	 * 
	 */
	public Event getEventById(Long id) {
		return this.eventDAO.get(id);
	}

	public List<Event> getByIdAndNombre(Long id, String nombre) {
		return this.eventDAO.getByIdAndNombre(id, nombre);
	}
	
	public List<Event> getFromPagination(String jsonString)
	{
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get("events");
		
		int count = jsonArray.length(); // get totalCount of all jsonObjects
		
		count = 10;
		for(int i=0 ; i< count; i++){   // iterate through jsonArray 
			JSONObject eventJsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 

			Gson gson = new Gson();
			EventBrite eventBrite = new EventBrite();
			eventBrite = gson.fromJson(eventJsonObject.toString(), EventBrite.class);
			
			try {
				DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date date = iso8601.parse(eventBrite.getStart().getLocal());
				
				SimpleDateFormat simpleDateFormat_Date = new SimpleDateFormat("yyyy-MM-dd");
				String fechaEvento = simpleDateFormat_Date.format(date);
				
				SimpleDateFormat simpleDateFormat_Time = new SimpleDateFormat("HH:mm:ss");
				String horaEvento = simpleDateFormat_Time.format(date);
				
				Event event = new Event(Long.valueOf(eventBrite.getId()),
						eventBrite.getName().getText(), 
						fechaEvento, 
						horaEvento);

				this.eventDAO.create(event);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			
		}
		return null;
	}

}
