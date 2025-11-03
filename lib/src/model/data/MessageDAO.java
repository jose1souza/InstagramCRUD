package model.data;

import java.util.List;

import model.Message;
import model.ModelException;

public interface MessageDAO {
	void save(Message message) throws ModelException;
	void update(Message message) throws ModelException;
	void delete(Message message) throws ModelException;
	List<Message> findAll() throws ModelException;
}
