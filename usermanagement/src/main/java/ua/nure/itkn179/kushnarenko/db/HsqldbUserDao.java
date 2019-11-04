package ua.nure.itkn179.kushnarenko.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.itkn179.kushnarenko.User;

class HsqldbUserDao implements Dao<User> {
	
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";

	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao() {
    }
	
	public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
	
	public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
	
	@Override
	public User create(User entity) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, new Date(entity.getDateofBirth().getTime()));
            
            int numberOfRows = statement.executeUpdate();
            if(numberOfRows!=1) {
            	throw new DatabaseException("Number of inserted srows: " + numberOfRows);
            }
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if (keys.next()) {
				entity.setId(new Long(keys.getLong(1)));
			}
            
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
			return entity;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void update(User entity) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User entity) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User find(long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		try {
			Collection<User> result = new LinkedList<>();
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
	                User user = new User();
	                user.setId(new Long(resultSet.getLong(1)));
	                user.setFirstName(resultSet.getString(2));
	                user.setLastName(resultSet.getString(3));
	                user.setDateofBirth(resultSet.getDate(4));
	                result.add(user);
	        }
			return result;
		}catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
