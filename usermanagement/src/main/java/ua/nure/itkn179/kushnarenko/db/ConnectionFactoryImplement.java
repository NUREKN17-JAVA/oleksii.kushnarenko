package ua.nure.itkn179.kushnarenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImplement implements ConnectionFactory {
	private static final String USER = "connection.user";
	private static final String PASSWORD = "connection.password";
	private static final String URL = "connection.url";
	private static final String DRIVER = "connection.driver";
	
	
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public ConnectionFactoryImplement(String driver, String url, String user, String password) {
		this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
	}

	
	public ConnectionFactoryImplement(Properties properties) {
		 user = properties.getProperty(USER);
	     password = properties.getProperty(PASSWORD);
	     url = properties.getProperty(URL);
	     driver = properties.getProperty(DRIVER);
	}


	@Override
	public Connection createConnection() throws DatabaseException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e);
		}
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
