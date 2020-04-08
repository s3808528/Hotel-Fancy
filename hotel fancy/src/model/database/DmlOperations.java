package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import model.Hiring_Record;
import model.Room;

import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.InvalidInputException;
import model.DateTime;
import model.CityLodge;
import model.HOTEL_FANCY_CONSTANTS;

public class DmlOperations  {
	private Statement statement;
	private Connection conn;
	//private int PresentRoom;

	// constructor
	public DmlOperations() throws ClassNotFoundException, SQLException, DatabaseException {
		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
		if (conn != null) {
			statement = conn.createStatement();
		} else {
			throw new DatabaseException("Connection to DB failed");
		}
	}

	// insert room details
	public void insert_Room_Entries(String roomId, int bedCount, String features, String roomType, String status,
			DateTime lastMaintenanceDate, String imagePath) throws SQLException, InvalidInputException {

		if (statement == null) {
			statement = conn.createStatement();
		}
		if (roomType.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			lastMaintenanceDate = null;
		}
		if(roomId.equals(null)) {
			throw new InvalidInputException("RoomId is Null");
		}
		String query = String.format("INSERT INTO %s VALUES ('%s',%d,'%s','%s','%s','%s','%s')",
				HOTEL_FANCY_CONSTANTS.TABLE_1_NAME, roomId, bedCount, features, roomType, status, lastMaintenanceDate,
				imagePath);
		int result = statement.executeUpdate(query);
		conn.commit();

		System.out.println("Insert into table " + HOTEL_FANCY_CONSTANTS.TABLE_1_NAME + " executed successfully");
		System.out.println(result + " row(s) affected");
	}

	// insert record entries into hiring record table
	public void insertRecordEntries(String Record_Id, String Rent_Date, String Estimated_Return_Date,
			String Actual_Return_Date, Double Rental_Fee, Double Late_Fee, String Room_Id) throws SQLException {
		if (statement == null) {
			statement = conn.createStatement();
		}
		String query = String.format("INSERT INTO %s VALUES ('%s','%s','%s','%s',%f,%f,'%s' )",
				HOTEL_FANCY_CONSTANTS.TABLE_2_NAME, Record_Id, Estimated_Return_Date, Rent_Date, Actual_Return_Date,
				Rental_Fee, Late_Fee, Room_Id);
		int result = statement.executeUpdate(query);
		
		
		conn.commit();
		System.out.println("Insert into table " + HOTEL_FANCY_CONSTANTS.TABLE_2_NAME + " executed successfully");
		System.out.println(result + " row(s) affected");

	}
	
	

	// call this at load of application and use in citylodge
	public void getRoomEntries(CityLodge cl) throws ClassNotFoundException, SQLException, DatabaseException, InvalidBedCountException {
		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
		statement = conn.createStatement();
		@SuppressWarnings("unused")
		HashMap<String, Room> rooms = new HashMap<>();
	
		String roomId;
		String query = String.format("SELECT * FROM %s", HOTEL_FANCY_CONSTANTS.TABLE_1_NAME);
		
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			System.out.println("getquery");
			if (resultSet.getString("Room_Type").equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
				roomId = resultSet.getString("Room_ID");
				System.out.println(roomId +" A " +resultSet.getString("Status"));
				System.out.println("getquery");
				//DateTime lastMaintenanceDate = new DateTime(resultSet.getString("Last_Mantainance_Date"));
				cl.createDatabaseImported_room(roomId, resultSet.getString("Room_Type"), resultSet.getInt("Bed_Count"),
						null, resultSet.getString("Features"), resultSet.getString("Image_Path"),
						resultSet.getString("Status"));
				
				cl.setHiringRecord(getHiringEntries(roomId),roomId);
				//getHiringEntries(roomId);
		
			} else {
				roomId = resultSet.getString("Room_ID");
				DateTime lastMaintenanceDate = new DateTime(resultSet.getString("Last_Mantainance_Date"));
				cl.createDatabaseImported_room(roomId, resultSet.getString("Room_Type"), resultSet.getInt("Bed_Count"),
						lastMaintenanceDate, resultSet.getString("Features"), resultSet.getString("Image_Path"),
						resultSet.getString("Status"));
				System.out.println("getquery");
				
				cl.setHiringRecord(getHiringEntries(roomId),roomId);
				//getHiringEntries(roomId);
			}
		}
	}

	// call this at load of application and use in rooms
	public ArrayList<Hiring_Record> getHiringEntries(String roomId)
			throws ClassNotFoundException, SQLException, DatabaseException {
		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
		statement = conn.createStatement();
		Hiring_Record hr;
		System.out.println("In Database");
		ArrayList<Hiring_Record> records = new ArrayList<>();
		String query = String.format("SELECT * FROM %s WHERE Room_ID='%s'", HOTEL_FANCY_CONSTANTS.TABLE_2_NAME,roomId);
		
		ResultSet Result_Set = statement.executeQuery(query);
		
		while (Result_Set.next()) {
			
			DateTime rent_Date = new DateTime(Result_Set.getString("Rent_Date"));
			System.out.println(Result_Set.getString("Room_ID"));
			System.out.println(Result_Set.getString("Est_Return_Date"));
			System.out.println(Result_Set.getString("Actual_Return_Date"));
			DateTime estimated_Return_Date = new DateTime(Result_Set.getString("Est_Return_Date"));
			DateTime actual_Return_Date = new DateTime(Result_Set.getString("Actual_Return_Date"));
			hr = new Hiring_Record(rent_Date, Result_Set.getString("Record_ID"), estimated_Return_Date, actual_Return_Date,
					Result_Set.getDouble("Rental_Fee"), Result_Set.getDouble("Late_Fee"));
			records.add(hr);
	
		}
		return records;
	}

	// update the record details using the recordId -use in room setreturndetails
	public void updateHiringRecordDetails(String recordId,String actualReturnDate, Double rentalFee, Double lateFee)
			throws ClassNotFoundException, SQLException {
		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
Statement st = conn.createStatement();
		
		ResultSet rs = st.executeQuery("SELECT Record_ID FROM HIRINGRECORDS");

		while(rs.next()) {
		   System.out.println(rs.getString("Record_ID"));
		}
		statement = conn.createStatement();
		String query = String.format(
				"UPDATE %s SET Actual_Return_Date = '%s', Rental_Fee = '%f', Late_Fee = '%f' WHERE Record_ID = '%s'",
				HOTEL_FANCY_CONSTANTS.TABLE_2_NAME, actualReturnDate, rentalFee, lateFee, recordId);
		int result = statement.executeUpdate(query);
		System.out.println("Update table " + HOTEL_FANCY_CONSTANTS.TABLE_2_NAME + " executed successfully");
		System.out.println(result + " row(s) affected");
	}

	// update room status
	public void updateRoomStatus(String roomId, String status) throws SQLException {
		statement = conn.createStatement();
		String query = String.format("UPDATE %s SET Status = '%s' WHERE Room_ID = '%s'", HOTEL_FANCY_CONSTANTS.TABLE_1_NAME,
				status, roomId);
		int result = statement.executeUpdate(query);
		System.out.println("Update table " + HOTEL_FANCY_CONSTANTS.TABLE_1_NAME + " executed successfully");
		System.out.println(result + " row(s) affected");
	}

	// update last maintenance date
	public void updateLastMaintenanceDate(String roomId, DateTime dateTime) throws SQLException {
		statement = conn.createStatement();
		String query = String.format("UPDATE %s SET Last_Mantainance_Date = '%s' WHERE Room_ID = '%s'",
				HOTEL_FANCY_CONSTANTS.TABLE_1_NAME, dateTime, roomId);
		int result = statement.executeUpdate(query);
		System.out.println("Update table " + HOTEL_FANCY_CONSTANTS.TABLE_1_NAME + " executed successfully");
		System.out.println(result + " row(s) affected");
	}
}
