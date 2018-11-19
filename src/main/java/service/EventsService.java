package service;

public class EventsService {

	public static int getEventCount(int days) {

		return ManagementService.getEventsDAO().getEventsCount(days);
	}

}
