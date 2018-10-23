package model;

import java.util.Date;

public class Session {

	private Integer sessionID;

	private Integer userId;
    private String token;
    private Date lastLogin;
    
	public Session(Integer userId, String token, Date lastLogin) {
		this.userId = userId;
		this.token = token;
		this.lastLogin = lastLogin;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
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
