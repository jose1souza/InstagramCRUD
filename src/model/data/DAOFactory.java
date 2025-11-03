package model.data;

import model.data.mysql.MySQLMessageDAO;
import model.data.mysql.MySQLPostDAO;
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {
	
	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}
	
	public static PostDAO createPostDAO() {
		return new MySQLPostDAO();
	}
	
	public static MessageDAO createMessageDAO() {
		return new MySQLMessageDAO();
	}
}
