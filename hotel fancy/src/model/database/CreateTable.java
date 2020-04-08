package model.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.HOTEL_FANCY_CONSTANTS;
import model.Exceptions.DatabaseException;

public class CreateTable {
	/**
	 * This method is used to create table 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws DbException 
	 */
	public void create_Table() throws SQLException, ClassNotFoundException, DatabaseException {
		// use try-with-resources Statement
		try (Connection con = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
				Statement stmt = con.createStatement();) {
			String tableNames[] = { HOTEL_FANCY_CONSTANTS.TABLE_1_NAME, HOTEL_FANCY_CONSTANTS.TABLE_2_NAME };
			for (int tableCount = 0; tableCount < HOTEL_FANCY_CONSTANTS.TABLE_COUNT; tableCount++) {
				try {
					check_Table_Exisit(con, tableNames[tableCount]);
				} catch (DatabaseException dbe) {
					int result = -1;
					if (tableCount == 0) {
						result = stmt.executeUpdate("CREATE TABLE " + tableNames[tableCount]
								+ "(Room_ID varchar(10), Bed_Count numeric,Features varchar(100),Room_Type varchar(50),Status varchar(50),Last_Mantainance_Date varchar(50),Image_Path varchar(100),primary key(Room_ID))");
					} else {
						result = stmt.executeUpdate("CREATE TABLE " + tableNames[tableCount]
								+ "(Record_ID varchar(100), Est_Return_Date varchar(15), Rent_Date varchar(50), Actual_Return_Date varchar(50), Rental_Fee float, Late_Fee float, Room_ID varchar(100), primary key(Record_ID))");
					}
					if (result == 0) {
						System.out.println("Table " + tableNames[tableCount] + " created successfully");
					} else {
						throw new DatabaseException("Table " + tableNames[tableCount] + " is not created");
					}
				}
			}
		}
	}

	//used to check whether the table exists
	public void check_Table_Exisit(Connection con, String tableName) throws SQLException, DatabaseException {
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(), null);
		if (tables != null) {
			if (!tables.next()) {
				throw new DatabaseException("Table " + tableName + " does not exist.");
			}
			tables.close();
		} else {
			throw new DatabaseException("Problem with retrieving database metadata");
		}
	}
}
