package ua.nure.itkn179.kushnarenko.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.itkn179.kushnarenko.User;

public class MockUserDao implements Dao<User> {
	private Long id = (long) 0;
	private Map<Long, User> users = new HashMap<>();

	public User create(User user) throws DatabaseException {
		Long currentId = new Long(++id);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}

	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
	}

	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}


	public User find(long id) throws DatabaseException {
		return (User) users.get(id);
	}

	public Collection<User> findAll() throws DatabaseException {
		return users.values();
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		// TODO Auto-generated method stub
		
	}
}
