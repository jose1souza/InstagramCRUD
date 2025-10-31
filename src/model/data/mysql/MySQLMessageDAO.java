package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Message;
import model.ModelException;
import model.Post;
import model.User;
import model.data.DAOFactory;
import model.data.DAOUtils;
import model.data.MessageDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLMessageDAO implements MessageDAO {

	@Override
	public void save(Message message) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "INSERT INTO messages VALUES " + "(DEFAULT, ?,CURDATE(),?,?)";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, message.getContent());
			preparedStatement.setString(2, message.getNameUserSend());
			preparedStatement.setString(3, message.getNameUserReceinder());

			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir messagem no BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(Message message) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();
			
			String sqlUpdate = "UPDATE messages \r\n"
					+ "SET \r\n"
					+ "    content = ?,\r\n"
					+ "    date_message = CURDATE(),\r\n"
					+ "    sender_id = ?,\r\n"
					+ "    receiver_id = ?\r\n"
					+ "WHERE\r\n"
					+ "    id_message = ?; ";
			
			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, message.getContent());
			preparedStatement.setInt(2, message.getUserSend().getId());
			preparedStatement.setInt(3, message.getUserReceinder().getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar messagem do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(Message message) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();  

			String sqlDelete = "delete from messages where id = ?;";

			preparedStatement = connection.prepareStatement(sqlDelete);
			preparedStatement.setInt(1, message.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao excluir a mensagem do BD.", sqle);
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}

	}

	@Override
	public List<Message> findAll() throws ModelException {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<Message> messagesList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			statement = connection.createStatement();
			String sqlSeletc = "SELECT \r\n"
					+ "    m.id_message,\r\n"
					+ "    m.content,\r\n"
					+ "    m.date_message,\r\n"
					+ "    sender.id AS sender_id,\r\n"
					+ "    sender.nome AS sender_nome,\r\n"
					+ "    sender.sexo AS sender_sexo,\r\n"
					+ "    sender.email AS sender_email,\r\n"
					+ "    receiver.id AS receiver_id,\r\n"
					+ "    receiver.nome AS receiver_nome,\r\n"
					+ "    receiver.sexo AS receiver_sexo,\r\n"
					+ "    receiver.email AS receiver_email\r\n"
					+ "FROM\r\n"
					+ "    messages m\r\n"
					+ "        JOIN\r\n"
					+ "    users sender ON m.sender_id = sender.id\r\n"
					+ "        JOIN\r\n"
					+ "    users receiver ON m.receiver_id = receiver.id\r\n"
					+ "ORDER BY m.date_message DESC;";

			rs = statement.executeQuery(sqlSeletc);

			setUpUsers(rs, messagesList);
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar mensagens do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}

		return messagesList;
	}
	
	private void setUpUsers(ResultSet rs, List<Message> messagesList)
            throws SQLException, ModelException {
		
		while (rs.next()) {
			int messageId = rs.getInt("id"); 
			String messageContent = rs.getString("content");
			Date messageDate = rs.getDate("message_date");
			int userSendId = rs.getInt("user_id");
			int userReceiverId = rs.getInt("user_id");

			Message newMessage = new Message(messageId);
			newMessage.setContent(messageContent);
			newMessage.setDate(messageDate);

			User messageUser = DAOFactory.createUserDAO().findById(userSendId);
			newMessage.setUserSend(messageUser);
			
			messagesList.add(newMessage);
		}
	}
	

	/*@Override
	public List<Message> findBySenderId(int senderId) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findByReceiverId(int receiverId) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}*/

}
