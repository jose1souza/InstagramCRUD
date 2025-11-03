package controller;

import java.util.List;
import model.User;
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

	// Listagem
	public void loadUsers() {
		try {
			List<User> users = userDAO.findAll();
			userListView.setUserList(users);
		} catch (ModelException e) {
			userListView.showMessage("Erro ao carregar usuários: " + e.getMessage());
		}
	}

	// Salvar ou atualizar
	public void saveOrUpdate(boolean isNew) {
		User user = userFormView.getUserFromForm();

		try {
			user.validate();
		} catch (IllegalArgumentException e) {
			userFormView.showErrorMessage("Erro de validação: " + e.getMessage());
			return;
		}

		try {
			if (isNew) {
				userDAO.save(user);
				userFormView.showInfoMessage("Usuário salvo com sucesso!");
			} else {
				userDAO.update(user);
				userFormView.showInfoMessage("Usuário atualizado com sucesso!");
			}
			userFormView.close();
		} catch (ModelException e) {
			userFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
		}
	}

	// Excluir
	/*public void excluirUsuario(User user) {
		try {
			userDAO.delete(user);
			userListView.showMessage("Usuário excluído!");
			loadUsers();
		} catch (ModelException e) {
			userListView.showMessage("Erro ao excluir: " + e.getMessage());
		}
	}*/

	public void setUserFormView(IUserFormView userFormView) {
		this.userFormView = userFormView;
	}

	public void setUserListView(IUserListView userListView) {
		this.userListView = userListView;
	}
}
