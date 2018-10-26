package model;

import java.util.Date;

public class Session {

	private Integer sessionID;

	private String userId;
    private String token;
    private Date lastLogin;
    
	public Session(String userId, String token, Date lastLogin) {
		this.userId = userId;
		this.token = token;
		this.lastLogin = lastLogin;
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
