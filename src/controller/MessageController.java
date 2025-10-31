package controller;

import java.util.List;

import model.Message;
import model.ModelException;
import model.Post;
import model.User;
import model.data.DAOFactory;
import model.data.MessageDAO;
import view.swing.message.IMessageFormView;
import view.swing.message.IMessageListView;
import view.swing.post.IPostFormView;
import view.swing.post.IPostListView;

public class MessageController {
    private final MessageDAO messageDAO = DAOFactory.createMessageDAO();
    private IMessageListView messageListView;
    private IMessageFormView messageFormView;
    
 // Listagem
    public void loadMessages() {
        try {
            List<Message> messages = messageDAO.findAll();
            messageListView.setMessageList(messages);
        } catch (ModelException e) {
            messageListView.showMessage("Erro ao carregar mensagens: " + e.getMessage());
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
            } else {
                messageDAO.update(message);
            }
            messageFormView.showInfoMessage("Mensagem salvo com sucesso!");
            messageFormView.close();
        } catch (ModelException e) {
            messageFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // Excluir
    public void excluirMessage(Message message) {
        try {
            messageDAO.delete(message);
            messageListView.showMessage("Mensagem excluído!");
            loadMessages();
        } catch (ModelException e) {
            messageListView.showMessage("Erro ao excluir post: " + e.getMessage());
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
