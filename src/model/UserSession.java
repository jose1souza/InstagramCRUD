package model;

public final class UserSession {
	private static UserSession instance; // Padrão Singleton
	private User user;
	
	public UserSession(User user){
		this.user = user;
	}
	
	public static void create(User user) {
		instance = new UserSession(user);
	}
	
	public static UserSession getInstance(){
		return instance;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
