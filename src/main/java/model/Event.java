package model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

@Entity("events")
public class Event extends BaseMongoDO  {

	private Long eventBriteID;
	private Date created;

    // Morphia needs a zero-arg constructor to create the class before populating its fields.
    public Event() {
       super();
    }

    public Event(Long eventBriteID) {
		Date now = new Date();
		this.setEventBriteID(eventBriteID);
		this.setCreated(now);
	}

	public Long getEventBriteID() {
		return eventBriteID;
	}

	public void setEventBriteID(Long eventBriteID) {
		this.eventBriteID = eventBriteID;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
