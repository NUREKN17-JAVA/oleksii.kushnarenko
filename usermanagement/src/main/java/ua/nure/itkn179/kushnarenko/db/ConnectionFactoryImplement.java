package ua.nure.itkn179.kushnarenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImplement implements ConnectionFactory {

	public ConnectionFactoryImplement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection createConnection() throws DatabaseException {
		String driver = "org.hsqldb.jdbcDriver";
		String url = "jdbc:hsqldb:file:db/usermanagement";
		String user = "sa";
		String password = "";
		
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
