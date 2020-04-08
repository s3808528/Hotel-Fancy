package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.CityLodge;
import model.HOTEL_FANCY_CONSTANTS;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;


public class DatabaseConnection {
	public DatabaseConnection(CityLodge cl) throws ClassNotFoundException, SQLException, DatabaseException, InvalidBedCountException {
		String dbName = HOTEL_FANCY_CONSTANTS.DATABASE_NAME;
		Connection con = getConnection(dbName);
		DmlOperations dml = new DmlOperations();
		if(con != null) {
			//jfx alert
			System.out.println("Connection to database " + dbName + " created successfully");
			//System.out.println("here");
			dml.getRoomEntries(cl);
		} else {
			throw new ClassNotFoundException("Error establishing Connection to DB");
		}
	}


	/*
	 * Database files will be created in the "database" folder in the project. If no
	 * username or password is specified, the default SA user and an empty password
	 * are used
	 */
	public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
		Class.forName(HOTEL_FANCY_CONSTANTS.JDBC_DRIVER);
		return DriverManager.getConnection(HOTEL_FANCY_CONSTANTS.JDBC_LOCATION + dbName,
				HOTEL_FANCY_CONSTANTS.DB_USER_NAME, HOTEL_FANCY_CONSTANTS.DB_PASSWORD);
	}
}
