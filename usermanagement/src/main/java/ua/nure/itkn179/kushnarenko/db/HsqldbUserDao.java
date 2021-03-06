package ua.nure.itkn179.kushnarenko.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.itkn179.kushnarenko.User;

class HsqldbUserDao implements Dao<User> {
	
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id=?";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
	public static final String FIND_BY_NAME = "SELECT id, firstname, lastname, dateofbirth FROM USERS WHERE firstname=? AND lastname=?";

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
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, entity.getFirstName());
			statement.setString(2, entity.getLastName());
			statement.setDate(3, new Date(entity.getDateofBirth().getTime()));
			statement.setLong(4, entity.getId().longValue());
			
			int n_rows = statement.executeUpdate();
	        if (n_rows != 1) {
	        	throw new DatabaseException("Number of the updated rows: " + n_rows);
	        }
	        statement.close();
	        connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		
	}

	@Override
	public void delete(User entity) throws DatabaseException {
		try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, entity.getId().longValue());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the deleted rows: " + n);
            }
            statement.close();
            connection.close();
		} catch (SQLException e) {
            throw new DatabaseException(e);
        }
		
	}

	@Override
	public User find(long id) throws DatabaseException {
         try {
        	 User user = null;
        	 Connection connection = connectionFactory.createConnection();
        	 PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        	 statement.setLong(1, id);
        	 ResultSet resultSet = statement.executeQuery();
        	 if (resultSet.next()) {
        		 user = new User();
            	 user.setId(resultSet.getLong(1));
                 user.setFirstName(resultSet.getString(2));
                 user.setLastName(resultSet.getString(3));
                 user.setDateofBirth(resultSet.getDate(4));
             }
             
             resultSet.close();
             statement.close();
 	         connection.close();
             
             return user;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
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
			resultSet.close();
            statement.close();
	        connection.close();
			
			return result;
		}catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Collection<User> find(String firstName, String lastName) throws DatabaseException {
		Collection<User> result = new ArrayList<>();

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet usersResultSet = statement.executeQuery();

            while (usersResultSet.next()) {
                User user = new User();
                user.setId(usersResultSet.getLong(1));
                user.setFirstName(usersResultSet.getString(2));
                user.setLastName(usersResultSet.getString(3));
                user.setDateofBirth(usersResultSet.getDate(4));
                result.add(user);
            }

            usersResultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
	}

}
