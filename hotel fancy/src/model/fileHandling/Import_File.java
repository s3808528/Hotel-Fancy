package model.fileHandling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.Scanner;
import model.Exceptions.ImportExportException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.InvalidInputException;
import model.Exceptions.RoomNotAvailableException;
//import model.database.DmlOperations;
//import model.HOTEL_FANCY_CONSTANTS;
import model.CityLodge;
import model.Hiring_Record;
//import model.Standard_Room;
//import model.Suite;
//import model.Standard_Room;
//import model.Suite;
import model.Exceptions.DatabaseException;
import model.DateTime;

public class Import_File {
	

//	private Standard_Room standard_Room;
//	private Suite suite_Room;
	private String room_Id;

	// used to import room data from file
	public void import_File(String filePath,CityLodge cl) throws ImportExportException {
		Scanner sc = null;
		try {
			InputStream input_Stream = new FileInputStream(filePath);
			sc = new Scanner(input_Stream);
			while (sc.hasNextLine()) {
				String roomRecord[] = sc.nextLine().split(":");
				if (roomRecord.length > 6) {
					
					import_Suite_Room(roomRecord,cl);
				} else if (roomRecord.length == 6) {
					if (roomRecord[0].split("_").length > 2) {
						import_Rental_Record(roomRecord,cl);
					} else {
						import_Standard_Room(roomRecord,cl);
					}
				} else {
					throw new ImportExportException("invalid format recieved");
				}
			}
		} catch (FileNotFoundException fe) {
			throw new ImportExportException("invalid format recieved");
		} catch (Exception e) {
			throw new ImportExportException("invalid format recieved");
		} finally {
			sc.close();
		}
	}

	private void import_Rental_Record(String[] roomRecord,CityLodge cl) throws NumberFormatException, ClassNotFoundException, SQLException, DatabaseException, InvalidBedCountException, RoomNotAvailableException {
		DateTime rentDate = new DateTime(roomRecord[1]);
		DateTime estimatedReturnDate = new DateTime(roomRecord[2]);
		DateTime actualReturnDate = new DateTime(roomRecord[3]);
		Hiring_Record hr = new Hiring_Record(rentDate, roomRecord[0], estimatedReturnDate, actualReturnDate,
				Double.parseDouble(roomRecord[4]), Double.parseDouble(roomRecord[5]));
		String roomId=roomRecord[0].substring(0, 5);
		System.out.println("Record id is:"+roomId);
		cl.setHiringRecord(hr,roomId);
	
	}

	private void import_Standard_Room(String[] roomRecord,CityLodge cl)
			throws NumberFormatException, ClassNotFoundException, SQLException, DatabaseException, ImportExportException, InvalidInputException {
	
		room_Id = roomRecord[0];
		
		cl.createImported_room(room_Id, roomRecord[3], Integer.parseInt(roomRecord[1]),null, roomRecord[2], roomRecord[5],
				roomRecord[4]);

	}

	private void import_Suite_Room(String[] roomRecord,CityLodge cl)
			throws NumberFormatException, ClassNotFoundException, SQLException, DatabaseException, ImportExportException, InvalidInputException {

		room_Id = roomRecord[0];
	
		DateTime last_Maintenance_Date=new DateTime(roomRecord[5]);

		cl.createImported_room(room_Id, roomRecord[3], Integer.parseInt(roomRecord[1]),last_Maintenance_Date, roomRecord[2], roomRecord[6],
				roomRecord[4]);
		//System.out.println("DONE"); 
	}

		

	}


