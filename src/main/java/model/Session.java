package model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("sessions")
public class Session extends BaseMongoDO {

	private Integer sessionID;

    private String token;
    private Date lastLogin;
    @Reference
    public User user;
    
    // Morphia needs a zero-arg constructor to create the class before populating its fields.
    public Session() {
       super();
    }
       
	public Session(User user2, String token2, Date lastLogin2) {
		this.setUser(user2);
		this.setToken(token2);
		this.setLastLogin(lastLogin2);
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
    
	public Integer getSessionID() {
		return sessionID;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
