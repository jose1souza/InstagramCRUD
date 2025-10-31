package model;

import java.util.Date;

public class Message{
	private int id;
	private String content;
	private Date date;
	private User user_send;
	private User user_receinder;
	
	public Message(int id) {
		this.id = id; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUserSend() {
		return user_send;
	}
	
	public User getUserReceinder() {
		return user_receinder;
	}
	
	public String getNameUserSend() {
		return user_send.getName();
	}
	
	public String getNameUserReceinder() {
		return user_receinder.getName();
	}

	public void setUserSend(User user_send) {
		this.user_send = user_send;
	}

	public void setUserReceinder(User user_receinder) {
		this.user_receinder = user_receinder;
	}
	
	public void validate() {	
		if (content == null || content.isBlank()) {
			throw new IllegalArgumentException("Conteúdo da mensagem não pode ser vazio.");
		}
		
		if (user_send == null || user_send.getId() <= 0) {
			throw new IllegalArgumentException("Usuário inválido para o envio da mensagem.");
		}
		if(user_receinder == null  || user_receinder.getId() <= 0) {
			throw new IllegalArgumentException("Usuário inválido para o envio da mensagem");
		}
		if (user_receinder.getId() == user_send.getId()) {
			throw new IllegalArgumentException("Você não pode enviar uma mensagem para si mesmo");
		}
		
	}
	
	
}
