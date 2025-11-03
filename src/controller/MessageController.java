package controller;

import java.util.List;

import model.Message;
import model.ModelException;
import model.User;
import model.UserSession;
import model.data.DAOFactory;
import model.data.MessageDAO;
import view.swing.message.IMessageFormView;
import view.swing.message.IMessageListView;

public class MessageController {
	private final MessageDAO messageDAO = DAOFactory.createMessageDAO();
	private IMessageListView messageListView;
	private IMessageFormView messageFormView;
	private final UserSession userSession;

	public MessageController(UserSession user) {
		this.userSession = user;
	}

	// Listagem
	public void loadMessagesSender() {
		try {
			int senderId = userSession.getUser().getId();
			List<Message> messages = messageDAO.findBySenderId(senderId);
			messageListView.setMessageList(messages);
		} catch (ModelException e) {
			messageListView.showMessage("Erro ao carregar mensagens enviadas: " + e.getMessage());
		}
	}
	
	public void loadMessagesReceiver() {
		try {
			int receiverId = userSession.getUser().getId();
			List<Message> messages = messageDAO.findByReceiverId(receiverId);
			messageListView.setMessageList(messages);
		} catch (ModelException e) {
			messageListView.showMessage("Erro ao carregar mensagens recebidas: " + e.getMessage());
		}
	}
	
	// Salvar ou atualizar
	public void saveOrUpdate(boolean isNew) {
		Message message = messageFormView.getMessageFromForm();

		try {
			message.validate();
		} catch (IllegalArgumentException e) {
			messageFormView.showErrorMessage("Erro de validação: " + e.getMessage());
			return;
		}

		try {
			if (isNew) {
				messageDAO.save(message);
			}
			if(message.getId() == userSession.getUser().getId()) {
				messageFormView.showInfoMessage("Você não pode editar mensagens de outros usuários!");
			}
			else {
				messageDAO.update(message);
			}
			messageFormView.showInfoMessage("Mensagem salva com sucesso!");
			messageFormView.close();
		} catch (ModelException e) {
			messageFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
		}
	}

	// Excluir
	public void excluirMessage(Message message) {
		try {
			messageDAO.delete(message);
			messageListView.showMessage("Mensagem excluída!");
			loadMessagesSender();
		} catch (ModelException e) {
			messageListView.showMessage("Erro ao excluir mensagens: " + e.getMessage());
		}
	}

	public void setMessageFormView(IMessageFormView messageFormView) {
		this.messageFormView = messageFormView;
	}

	public void setMessageListView(IMessageListView messageListView) {
		this.messageListView = messageListView;
	}

	public List<User> getAllUsers() {
		try {
			return DAOFactory.createUserDAO().findAll();
		} catch (ModelException e) {
			messageFormView.showErrorMessage("Erro ao carregar usuários: " + e.getMessage());
			return List.of();
		}
	}

}
