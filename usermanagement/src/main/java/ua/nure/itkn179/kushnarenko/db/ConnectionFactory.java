package ua.nure.itkn179.kushnarenko.db;

import java.sql.Connection;

public interface ConnectionFactory {
	Connection createConnection() throws DatabaseException;
}
