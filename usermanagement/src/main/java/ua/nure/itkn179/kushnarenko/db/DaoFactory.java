package ua.nure.itkn179.kushnarenko.db;

import java.io.IOException;
import java.util.Properties;

import ua.nure.itkn179.kushnarenko.User;

public abstract class DaoFactory {
	private static final String SETTINGS_PROPERTIES = "settings.properties";
	protected static final String HSQLDB_USER_DAO = "dao.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	
	protected static Properties properties;
	private static DaoFactory instance;
	
	static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader()
                    .getResourceAsStream(SETTINGS_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static synchronized DaoFactory getInstance() {
		if (instance == null) {
            Class<?> factoryClass;
            try {
                factoryClass = Class.forName(properties
                        .getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

	protected DaoFactory() {
	}
	
	 public static void init(Properties prop) {
	        properties = prop;
	        instance = null;
	 }
	
	protected ConnectionFactory getConnectionFactory() {
	    return new ConnectionFactoryImplement(properties);
	}
	
	public abstract Dao<User> getUserDao();

}
