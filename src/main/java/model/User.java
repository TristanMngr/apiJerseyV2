package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Entity("users")
public class User extends BaseMongoDO {
	
	private String userName;
    private Integer userId;
    private String password;
    private Date lastLogin;
    
    @Reference
    private List<EventsList> eventsLists;

    @Embedded
    private List<Alarm> alarms;

	private String role;

    // TODO change list alarm and event
    public User() {
        this.role = "USER";
    }

    public User(String userName, String password) {
    	this.userName = userName;
    	this.password = password;
    	this.role = "USER";
    }

    public User(ObjectId objectId, String userName, String password) {
        this.userName = userName;
        this.setPassword(password);
        this.setId(objectId);
        this.setRole("USER");
    }

//    public User(String string) {
//        this.userName = string;
//        this.setAlarms(Arrays.asList(new Alarm(), new Alarm()));
//        this.setPassword("password");
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEventsLists(List<EventsList> eventsLists) {
        this.eventsLists = eventsLists;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<EventsList> getEventsLists() {
        return eventsLists;
    }

    public void setEventos(List<EventsList> eventsLists) {
        this.eventsLists = eventsLists;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
