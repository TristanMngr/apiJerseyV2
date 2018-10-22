package model;

public class User {

	private Integer userId;
    private String userName;
    private String password;

	public User(Integer userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
    

    public User(String userName2, String password2) {
		this.userName = userName2;
		this.password = password2;
	}


	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    
	
}
