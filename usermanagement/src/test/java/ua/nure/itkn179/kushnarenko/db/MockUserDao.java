package ua.nure.itkn179.kushnarenko.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.itkn179.kushnarenko.User;

public class MockUserDao implements Dao<User> {
	private Long id = (long) 0;
	private Map<Long, User> users = new HashMap<>();

	@Override
	public User create(User user) throws DatabaseException {
		Long currentId = new Long(++id);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}

	@Override
	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
	}

	@Override
	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}

	@Override
	public User find(long id) throws DatabaseException {
		return (User) users.get(id);
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		return users.values();
	}

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		// TODO Auto-generated method stub
		
	}
}
