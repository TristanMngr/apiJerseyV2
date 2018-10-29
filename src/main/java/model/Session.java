package model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("sessions")
public class Session extends BaseMongoDO {

	private Integer sessionID;

	private String userId;
    private String token;
    private Date lastLogin;
    @Reference
    public User user;
    
    // Morphia needs a zero-arg constructor to create the class before populating its fields.
    public Session() {
       super();
    }
    
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Session(String userId, String token, Date lastLogin) {
		this.userId = userId;
		this.token = token;
		this.lastLogin = lastLogin;
	}
	
	public Session(User user2, String token2, Date lastLogin2) {
		this.user = user2;
		this.token = token2;
		this.lastLogin = lastLogin2;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	
}
