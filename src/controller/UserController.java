package controller;

import java.util.List;
import model.User;
import model.UserSession;
import model.ModelException;
import model.data.DAOFactory;
import model.data.UserDAO;
import view.swing.user.IUserFormView;
import view.swing.user.IUserListView;

public class UserController {
	private final UserDAO userDAO = DAOFactory.createUserDAO();
	private IUserListView userListView;
	private IUserFormView userFormView;

	public void save(User user) throws ModelException {
		if (user == null) {
			throw new IllegalArgumentException("Usuário não pode ser nulo.");
		}

		if (user.getName() == null || user.getName().isBlank()) {
			throw new ModelException("Nome é obrigatório.");
		}
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new ModelException("Email é obrigatório.");
		}
		if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
			throw new ModelException("Senha é obrigatória.");
		}

		userDAO.save(user);
	}

	public void loadUsers() {
		try {
			User user = userDAO.findById(UserSession.getInstance().getUser().getId());
			userListView.setUser(user);
		} catch (ModelException e) {
			userListView.showMessage("Erro ao carregar usuários: " + e.getMessage());
		}
	}

	public void saveOrUpdate(boolean isNew) {
		User user = userFormView.getUserFromForm();

		try {
			user.validate();
		} catch (IllegalArgumentException e) {
			userFormView.showErrorMessage("Erro de validação: " + e.getMessage());
			return;
		}

		try {
			user.setId(UserSession.getInstance().getUser().getId());
			if (isNew) {
				userDAO.save(user);
				userFormView.showInfoMessage("Usuário salvo com sucesso!");
			} else {
				user.setPasswordHash(UserSession.getInstance().getUser().getPasswordHash());
				userDAO.update(user);
				userFormView.showInfoMessage("Usuário atualizado com sucesso!");
			}
			UserSession.getInstance().setUser(user);
			userFormView.close();
		} catch (ModelException e) {
			userFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
			System.out.println(user.getEmail());
		}
	}

	
	public void excluirUsuario(User user) {
		try {
			userDAO.delete(UserSession.getInstance().getUser());
			userListView.showMessage("Usuário excluído!");
		} catch (ModelException e) {
			userListView.showMessage("Erro ao excluir: " + e.getMessage());
		}
	}

	public void setUserFormView(IUserFormView userFormView) {
		this.userFormView = userFormView;
	}

	public void setUserListView(IUserListView userListView) {
		this.userListView = userListView;
	}
}
