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

			String sqlInsert = "INSERT INTO messages (content,date_message,sender_id, receiver_id)\r\n"
					+ "VALUES (?,NOW(),?,?);";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, message.getContent());
			preparedStatement.setInt(2, message.getUserSend().getId());
			preparedStatement.setInt(3, message.getUserReceiver().getId());

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
			preparedStatement.setInt(3, message.getUserReceiver().getId());
			preparedStatement.setInt(4, message.getId());
			
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

			String sqlDelete = "DELETE FROM messages "
					+ "WHERE "
					+ "id_message = ?;";

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
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    List<Message> messagesList = new ArrayList<>();
	    
	    try {
	        connection = MySQLConnectionFactory.getConnection();
	        
	        String sql = "SELECT \r\n"
	        		+ "    id_message, content, date_message, sender_id, receiver_id\r\n"
	        		+ "FROM\r\n"
	        		+ "    messages\r\n"
	        		+ "ORDER BY date_message DESC;";
	        preparedStatement = connection.prepareStatement(sql);
	        rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            int id = rs.getInt("id_message");
	            String content = rs.getString("content");
	            Date date = rs.getTimestamp("date_message");
	            int senderId = rs.getInt("sender_id");
	            int receiverId = rs.getInt("receiver_id");

	            Message message = new Message(id);
	            message.setContent(content);
	            message.setDate(date);
	            
	            User sender = DAOFactory.createUserDAO().findById(senderId);
	            User receiver = DAOFactory.createUserDAO().findById(receiverId);

	            message.setUserSend(sender);
	            message.setUserReceiver(receiver);

	            messagesList.add(message);
	    }}
	        catch (SQLException sqle) {
		        DAOUtils.sqlExceptionTreatement("Erro ao carregar mensagens do BD.", sqle);
		    } catch (ModelException me) {
		        throw me;
		    } finally {
		        DAOUtils.close(rs);
		        DAOUtils.close(preparedStatement);
		        DAOUtils.close(connection);
		    }

		    return messagesList;
	}

}
