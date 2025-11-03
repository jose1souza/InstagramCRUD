package view.swing.message;

import java.util.List;

import model.Message;

public interface IMessageListView {
	void setMessageList(List<Message> messages);
    void showMessage(String msg);
}
