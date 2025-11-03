package model.auth;

import model.ModelException;
import model.User;
import model.data.DAOFactory;
import model.data.UserDAO;

public class RegisterAuthenticator {

	    public boolean autheticathor(User user) throws ModelException {
	        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
	            throw new IllegalArgumentException("Email inválido para verificação.");
	        }

	        UserDAO userDAO = DAOFactory.createUserDAO();
	        User existingUser = userDAO.findByEmail(user.getEmail());

	        return existingUser != null; // true = já cadastrado
	    }

}
