package model.data;

import model.data.mysql.MySQLMessageDAO;
import model.data.mysql.MySQLPostDAO;
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {
	
//	private final static Map<Class, Object> daos = new HashMap<>();
//	
//	private DAOFactory() {}
//	
//	static {
//		daos.put(PostDAO.class, new MySQLPostDAO());
//		daos.put(UserDAO.class, new MySQLUserDAO());
//	}
//	
//	public static Object create (Class clazz) {
//		return daos.get(clazz);
//	}
	
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
