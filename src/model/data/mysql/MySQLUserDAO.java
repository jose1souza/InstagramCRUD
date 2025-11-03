package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Post;
import model.User;
import model.UserGender;
import model.data.DAOUtils;
import model.data.UserDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLUserDAO implements UserDAO {

	@Override
	public void save(User user) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlIsert = " INSERT INTO "
					        + " users VALUES "
					        + " (DEFAULT, ?, ?, ?,?); ";

			preparedStatement = connection.prepareStatement(sqlIsert);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getGender().toString());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPasswordHash());
			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir user do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(User user) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = "UPDATE users SET user_name = ?,\n"
					+ "gender = ?, email = ?, user_password = ? WHERE id = ?;";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getGender().toString());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPasswordHash());
			preparedStatement.setInt(5, user.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar user do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}		
	}

	@Override
	public void delete(User user) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM users WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, user.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar user do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}	
	}

	@Override
	public List<User> findAll() throws ModelException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List<User> usersList = new ArrayList<>();

		try {
			connection = MySQLConnectionFactory.getConnection();

			statement = connection.createStatement();
			String sqlSelect = " SELECT * FROM users order by user_name; ";

			rs = statement.executeQuery(sqlSelect);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("user_name");
				
				String genderStr = rs.getString("gender");
				UserGender gender = 
						genderStr.equals("M") ? UserGender.M : UserGender.F;
				
				String email = rs.getString("email");
				
				User user = new User(id);
				user.setName(name);
				user.setGender(gender);
				user.setEmail(email);
				
				List<Post> posts = new MySQLPostDAO().findByUserId(id);
				user.setPosts(posts);
				
				usersList.add(user);
			}

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao carregar posts do BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}

		return usersList;
	}

	@Override
	public User findById(int id) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		User user = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlSelect = "SELECT * FROM users WHERE id = ?;";
			preparedStatement = connection.prepareStatement(sqlSelect);
			preparedStatement.setInt(1, id);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("user_name");
				String genderStr = rs.getString("gender");
				UserGender gender = genderStr.equals("M") ? UserGender.M : UserGender.F;
				String email = rs.getString("email");

				user = new User(id);
				user.setName(name);
				user.setGender(gender);
				user.setEmail(email);
			}
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao buscar user por id no BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}

		return user;
	}

	@Override
	public User findByEmail(String email) throws ModelException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;

	    try {
	        connection = MySQLConnectionFactory.getConnection();
	        String sql = "SELECT \n"
	        		+ "    id, user_name, gender, email, user_password\n"
	        		+ "FROM\n"
	        		+ "    users\n"
	        		+ "WHERE\n"
	        		+ "    email = ?;";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, email);
	        rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            User user = new User(rs.getInt("id"));
	            user.setName(rs.getString("user_name"));
	            user.setGender(UserGender.valueOf(rs.getString("gender")));
	            user.setEmail(rs.getString("email"));
	            user.setPasswordHash(rs.getString("user_password"));
	            return user;
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        throw new ModelException("Erro ao buscar usuário por email.", e);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }
	}
}
