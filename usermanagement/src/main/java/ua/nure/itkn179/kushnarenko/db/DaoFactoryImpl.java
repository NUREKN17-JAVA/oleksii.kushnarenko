package ua.nure.itkn179.kushnarenko.db;

import ua.nure.itkn179.kushnarenko.User;

public class DaoFactoryImpl extends DaoFactory {

	@SuppressWarnings("unchecked")
	public Dao<User> getUserDao() {
		Dao<User> result = null;
		try {
			Class<?> hsqldbUserDaoClass = Class.forName(properties.getProperty(HSQLDB_USER_DAO));
			result = (Dao<User>) hsqldbUserDaoClass.newInstance();
            result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
