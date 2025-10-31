package view.swing.message;

import model.Message;

public interface IMessageFormView {
	Message getMessageFromForm();
    void setMessageInForm(Message message);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
