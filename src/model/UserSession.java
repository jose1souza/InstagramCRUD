package model;

public class UserSession {
	private User user;
	
	public UserSession(User user){
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
