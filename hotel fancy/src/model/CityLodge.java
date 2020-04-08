package model;

import java.io.File;

import java.io.FileNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;



import model.Exceptions.*;
import model.database.DmlOperations;
//just checking
public class CityLodge {

	//Scanner sread = new Scanner(System.in);
	String features;
	String auto_Gen_Room_Id;
	String room_Id = null;
	String room_Type = null;
	int bed_Count;
	DateTime last_Maintenance_Date;

	private int room_No = 0;// Number of rooms currently available protected
	String cust_Id;// Id of the customer protected
	String r_Id;// Room Id
	@SuppressWarnings("unused")
	private int Days;// Number of Days protected
	DateTime rentDate;// Date of rent
	private String ImagePath;
	@SuppressWarnings("unused")
	private int numberHired = 0;
	private String[] All_Id = new String[HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT];// All id's of the current room
	private Room room[] = new Room[HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT];
	private DmlOperations dml;
	private String status;

	/*
	 * This method is importing records for the hiring records on importing file
	 */public void setHiringRecord(Hiring_Record hr, String roomId) throws ClassNotFoundException, SQLException,
			DatabaseException, InvalidBedCountException, RoomNotAvailableException {
		System.out.println("here");
		String records[] = hr.getRecordId().split("_");
		String Id = records[0] + "_" + records[1];
		for (int i = 0; i < room_No; i++) {
			if ((room[i].get_Id()).equals(roomId)) {

				// room[i].[room[i].getNoHired()].setRent_Date(hr.getRent_Date());
				room[i].setRecords(Id, records[2], hr);
				System.out.println("record set");
			}
		}
	}

	/*
	 * This method is importing the hiring records for the database
	 */ public void setHiringRecord(ArrayList<Hiring_Record> arrayList, String roomId)
			throws InvalidBedCountException, SQLException, ClassNotFoundException, DatabaseException {
		System.out.println("i am called");
		for (int i = 0; i < this.room_No; i++) {
			System.out.println("allid " + room[i].get_Id());
			if (All_Id[i].equalsIgnoreCase(roomId)) {
				Iterator<Hiring_Record> itr = arrayList.iterator();
				while (itr.hasNext()) {
					Hiring_Record hr = new Hiring_Record();
					hr = (Hiring_Record) itr.next();
					System.out.println(hr.getRent_Date());

					// System.out.println(room[i].hiring[room[i].getNoHired()].getRent_Date());

					hr.setInitialRecords(hr, room[i]);

					room[i].hiring[room[i].getNoHired()] = new Hiring_Record();
					room[i].hiring[room[i].getNoHired()].setRent_Date(hr.getRent_Date());

					room[i].hiring[room[i].getNoHired()].setRecord_Id(hr.getRecord_Id());
					room[i].hiring[room[i].getNoHired()].setEstimated_Return_Date(hr.getEstimated_Return_Date());
					room[i].hiring[room[i].getNoHired()].setActual_Return_Date(hr.getActual_Return_Date());
					room[i].hiring[room[i].getNoHired()].setRental_Fee(hr.getRental_Fee());
					room[i].hiring[room[i].getNoHired()].setLate_Fee(hr.getLate_Fee());
					room[i].setNoHired(room[i].getNoHired() + 1);
					String current_State = this.toString();
					System.out.println("current_State:\t" + current_State);
					dml = new DmlOperations();

					System.out.println("rentDate" + room[i].hiring[room[i].getNoHired() - 1].getRent_Date());
				}

				System.out.println("Number hired" + room[i].getNoHired());
			}
		}
	}

	/*
	 * // methods // main menu public void runApp() { displayMenu();
	 * 
	 * }
	 */

	/*
	 * public void displayMenu() {
	 * 
	 * System.out.println("****** CITYLODGE MAIN MENU ******");
	 * System.out.println("Add Room:\t\t\t1");
	 * System.out.println("Rent Room:\t\t\t2");
	 * System.out.println("Return Room:\t\t\t3");
	 * System.out.println("Room Maintenance:\t\t4");
	 * System.out.println("Complete Maintenance:\t\t5");
	 * System.out.println("Display All Rooms:\t\t6");
	 * System.out.println("Exit Program:\t\t\t7");
	 * System.out.print("Enter your choice:\t\t\t"); try { Choices(); } catch
	 * (InvalidInputException e) { // View m=new View(); // m.fun();
	 * System.out.println(e); e.printStackTrace(); // displayMenu();
	 * 
	 * } catch (InvalidBedCountException e) { System.out.println(e); e.initCause(e);
	 * e.printStackTrace(); // displayMenu(); } catch (IncorrectReturnDateException
	 * e) { System.out.println(e); e.initCause(e); e.printStackTrace(); //
	 * displayMenu(); } catch (RoomNotAvailableException e) { System.out.println(e);
	 * e.initCause(e); e.printStackTrace(); // displayMenu(); } catch (Exception e)
	 * { System.out.println(e.getMessage()); e.printStackTrace(); // displayMenu();
	 * } }
	 */
	/*
	 * Menu for operating the CITYLODGE MAIN MENU
	 *//*
		 * private void Choices() throws InvalidInputException,
		 * InvalidBedCountException, IncorrectReturnDateException,
		 * RoomNotAvailableException, ClassNotFoundException, SQLException,
		 * DatabaseException, IOException {
		 * 
		 * int choice = Integer.parseInt(sread.next());
		 * 
		 * if (choice < 1 || choice > 7) { throw new
		 * InvalidInputException("You have made an invalid selection"); //
		 * System.out.println("You have entered an invalid selection, please try //
		 * again\n"); // displayMenu(); } else if (choice == 7) { //
		 * ExportFile.Export_File("C:/Users/Vaishali wahi/Myeclipse-workspace/hotel //
		 * fancy/src/sampl_data.txt",this);
		 * System.out.println("You have exited the program.\r\n"); System.exit(1); }
		 * else { System.out.println("You have entered " + choice + "\r\n"); switch
		 * (choice) { case 1: System.out.println("calling add room"); addRoom();
		 * 
		 * break;
		 * 
		 * case 2: System.out.println("calling rent room \n"); // getInRoomId(); //
		 * rentRoomBefore(); displayMenu(); break;
		 * 
		 * case 3: System.out.println("calling return room"); // getInRoomId(); //
		 * returnRoomBefore(); displayMenu(); break;
		 * 
		 * case 4: System.out.println("calling room mainatenance"); // getInRoomId(); //
		 * performMaintenanceBefore(); displayMenu(); break;
		 * 
		 * case 5: System.out.println("calling complete maintenance"); // getInRoomId();
		 * // completeMaintenanceBefore(); displayMenu(); break; case 6:
		 * System.out.println("calling display all rooms"); DisplayAllRooms();
		 * 
		 * break; // default: throw new RoomNotAvailableException("Room not available");
		 * } }
		 * 
		 * }
		 */
	/*
	 * calling perform maintenance
	 */public void performMaintenanceBefore(String roomId)
			throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		for (int i = 0; i < room_No; i++) {
			if ((room[i].get_Id()).equalsIgnoreCase(roomId)) {
				room[i].performMaintenance();
			}
		}
	}

	/*
	 * calling complete maintenance
	 */ public void completeMaintenanceBefore(String completeMaintenanceDate, String roomId)
			throws InvalidInputException, RoomNotAvailableException, SQLException, ClassNotFoundException,
			DatabaseException {
		System.out.println("\nMaintenance completion date (dd/mm/yyyy): ");
		DateTime completionDate;
		completionDate = get_Date(completeMaintenanceDate);
		for (int i = 0; i < room_No; i++) {
			System.out.println(room[i].get_Id());
			if ((room[i].get_Id()).equalsIgnoreCase(roomId)) {
				System.out.println("Inside" + room[i].get_Id());
				room[i].completeMaintenance(completionDate);
				if (room[i].getStatus().equals(HOTEL_FANCY_CONSTANTS.AVAILABLE)) {
					System.out.println("\n\tSuite " + room[i].get_Id()
							+ " has all maintenance operations completed and is now ready for rent. ");
				} else {
					System.out.println("\n\tMaintenance not completed.");
				}
			}
		}

	}

	/*
	 * This method os called befor return method in Room class @calling return room
	 */public void returnRoomBefore(String roomID, String returnDate)
			throws InvalidBedCountException, IncorrectReturnDateException, RoomNotAvailableException,
			InvalidInputException, SQLException, ClassNotFoundException, DatabaseException {
		System.out.println("In Return Room()" + roomID);
		DateTime Return_Date = get_Date(returnDate);

		for (int i = 0; i < room_No; i++) {
			int[] inputDate = Arrays.stream(returnDate.split("/")).mapToInt(Integer::parseInt).toArray();
			DateTime dt = new DateTime(inputDate[0], inputDate[1], inputDate[2]);
			room[i].setReturnDate(dt);
			if ((room[i].get_Id()).equals(roomID)) {

				// System.out.println(room[i].Calc_Rental_Fee(, room[i].get_Rental_Fee()));
				// System.out.println(room[i].getNumOfdays());
				// System.out.println(room[i].getBed_Count());
				room[i].setRentDate(room[i].hiring[room[i].getNoHired() - 1].getRent_Date());
				// System.out.println(room[i].getRentDate());
				// System.out.println(this.rentDate);
				room[i].returnRoom(Return_Date);
				// System.out.println("Room " + room[i].get_Id() + " is Returned. \t\n Thankyou
				// for Coming!!");
				System.out.println("\nSummary of the Room.\n");
				String Summarized;
				Summarized = room[i].toString();
				System.out.print("\n\t\tSUMMARY: " + Summarized + "\n");

				System.out.println("Room " + room[i].get_Id() + " is Returned. \t\n ");

			}
		}

	}

	/*
	 * This method is called before calling rent room in the main class
	 */public void rentRoomBefore(String roomId, String rentDate, String custId, String days)
			throws InvalidInputException, NumberFormatException, ClassNotFoundException, RoomNotAvailableException,
			SQLException, DatabaseException {
		for (int i = 0; i < room_No; i++) {
			// System.out.println(room[i].get_Id() + " i have " + roomId);
			if ((room[i].get_Id()).equalsIgnoreCase(roomId)) {
				// System.out.println("i am here");
				if (room[i].getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.AVAILABLE)) {
					// System.out.print("\tEnter the Customer id: \t CUS");
					room[i].setNumOfdays(Integer.parseInt(days));
					this.cust_Id = "CUS" + custId;
					room[i].setCustomerId(custId);

					int[] inputDate = Arrays.stream(rentDate.split("/")).mapToInt(Integer::parseInt).toArray();
					DateTime dt = new DateTime(inputDate[0], inputDate[1], inputDate[2]);
					// System.out.print("\tRent date(dd/mm/yyyy):\t");
					this.rentDate = get_Date(rentDate);
					room[i].setRentDate(dt);
					// System.out.print("\tHow many days?: ");
					// Days = sread.nextInt();
					room[i].rent(cust_Id, this.rentDate, Integer.parseInt(days));

					if (room[i].getStatus().equals(HOTEL_FANCY_CONSTANTS.RENTED)) {
						break;
					} else {
						throw new RoomNotAvailableException("Room not available");
					}

				} else {
					throw new RoomNotAvailableException("Room not available");
				}

			}
		}
	}

	/* Doing prerequisite work before calling rent */
	/*
	 * public void rentRoomBefore(String rentDate, String customerId) throws
	 * InvalidInputException, RoomNotAvailableException, ClassNotFoundException,
	 * SQLException, DatabaseException { for (int i = 0; i < room_No; i++) { if
	 * ((room[i].get_Id()).equals(r_Id)) { if
	 * (room[i].getStatus().equals(HOTEL_FANCY_CONSTANTS.AVAILABLE)) { //
	 * System.out.print("\tEnter the Customer id: \t CUS"); cust_Id = "CUS" +
	 * customerId; // System.out.print("\tRent date(dd/mm/yyyy):\t"); this.rentDate
	 * = get_Date(rentDate); System.out.print("\tHow many days?: "); // Days =
	 * sread.nextInt(); room[i].rent(cust_Id, this.rentDate, Days); if
	 * (room[i].getStatus().equals(HOTEL_FANCY_CONSTANTS.RENTED)) {
	 * System.out.println("Room " + room[i].get_Id() + " is now rented by customer "
	 * + cust_Id); } else { throw new
	 * RoomNotAvailableException("Room not available"); }
	 * 
	 * } else { System.out.println("Room " + room[i].get_Id() +
	 * " is not available at the moment"); } }
	 * 
	 * } }
	 */

	/*
	 * Calls the rent room and check if the room is rented
	 */public void rentRoomAfter(Room room)
			throws ClassNotFoundException, RoomNotAvailableException, SQLException, DatabaseException {

	}

	// This method is used to add new rooms to the rooms collection
	public void addRoom() throws InvalidInputException, ClassNotFoundException, SQLException, DatabaseException,
			FileNotFoundException {
		System.out.println("In addRoom():\t\t\t" + this.room_Id);
		dml = new DmlOperations();
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			room[room_No] = new Standard_Room(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);

			dml.insert_Room_Entries(this.room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date,
					ImagePath);
			System.out.println("Room Created");
			room[room_No].display();

			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		} else {
			room[room_No] = new Suite(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);

			dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date, ImagePath);
			System.out.println("Room Created");
			room[room_No].display();
			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		}

		// System.out.print("Do you want to auto generate Room Id, Enter Y (for Yes) or
		// N (for No):\t\t\t\r");
		/*
		 * auto_Gen_Room_Id = sread.next().toUpperCase();
		 * 
		 * if (auto_Gen_Room_Id.equalsIgnoreCase("N")) { System.out
		 * .print("Enter Room ID \n\teg:\n \t1. Standard room:\t R_102 \n \t2. Suite room:\t S_102 ):\t\t\t"
		 * ); room_Id = sread.next().toUpperCase(); if
		 * (room_Id.matches("(?i)[R,S]_\\d+")) { if (containId(room_Id)) {
		 * System.out.println("Room Already Present "); addRoom(); } } else {
		 * System.out.println("Invalid Room"); addRoom(); }
		 */

		/*
		 * create_room(room_Id, room_Type, bed_Count, last_Maintenance_Date, features,
		 * ImagePath, HOTEL_FANCY_CONSTANTS.AVAILABLE); // displayMenu();
		 */

	}

	/*
	 * This method is getting the path of the image for the particular room chosen
	 * by the user
	 */
	private String get_Image(String imagePath) throws FileNotFoundException, InvalidFileException {
		File input = new File(imagePath);
		if (input.isFile() || imagePath.equalsIgnoreCase(null)) {
			this.ImagePath = imagePath;
		} else {
			this.ImagePath = "../hotel fancy/Images/No_image_available.svg";
		}

		return this.ImagePath;

	}

	private void setRoomID(String roomId) throws RoomNotAvailableException {
		System.out.println("Set the room Id" + roomId);
		if (!containId(roomId)) {
			this.room_Id = roomId;
		} else {
			throw new RoomNotAvailableException("Room is already present");
		}
	}

	/*
	 * get room_Id and check if it is present or not
	 */private void getInRoomId(String roomId) throws InvalidInputException {
		System.out.print("Setting Up Room ID");

		if (!containId(roomId)) {
			// System.out.println("\t\t YOU ENTERED A WRONG ROOM ID\n");
			// System.out.println("\t Enter the room Id");
			throw new InvalidInputException("YOU ENTERED A WRONG ROOM ID");
			// r_Id = sread.next().toUpperCase();
		}

	}

	// Creating new room
	public void create_room(String room_Id, String room_Type, int bed_Count, DateTime last_Maintenance_Date,
			String features, String ImagePath, String status)
			throws ClassNotFoundException, SQLException, DatabaseException, InvalidInputException {
		dml = new DmlOperations();
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			room[room_No] = new Standard_Room(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);

			dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date, ImagePath);
			if (!containId(room_Id)) {
				this.All_Id[room_No] = room_Id;
			}
			System.out.println("Room Created");
			room[room_No].display();

			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		} else {
			room[room_No] = new Suite(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);
			if (!containId(room_Id)) {
				this.All_Id[room_No] = room_Id;
			}

			dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date, ImagePath);
			System.out.println("Room Created");
			room[room_No].display();
			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		}

	}

	/*
	 * This method with take the input features and make a better summary to be
	 * stored in database and validate features
	 */
	private String get_features(String features) throws InvalidInputException {
		// System.out.print("Enter the room features (upto 20 words):\t");
		// String features = sread.next();
		// features += sread.nextLine();
		String[] words = new String[20];
		words = features.split("\\s+|,");
		// System.out.println(words[0]);
		String featureSummary = words[0];
		if (features.length() < 20) {
			for (int i = 0; i < words.length - 1; i++) {

				if (i % 2 == 0) {
					featureSummary = String.join(" ", featureSummary, words[i + 1]);
					// System.out.println(featureSummary);
				} else {
					featureSummary = String.join(", ", featureSummary, words[i + 1]);
					// System.out.println(featureSummary);
				}
			}
		} else {
			throw new InvalidInputException("Features can be upto 20 in length");
		}

		return featureSummary;
	}

	/*
	 * get the date
	 */public DateTime get_Date(String date) throws InvalidInputException {
		DateTime Date = null;
		try {
			int[] inputDate = Arrays.stream(date.split("/")).mapToInt(Integer::parseInt).toArray();
			DateTime dt = new DateTime(inputDate[0], inputDate[1], inputDate[2]);
			if (DateTime.validateDate(dt)) {
				Date = dt;
			} else {
				throw new InvalidInputException(date);
				// System.out.println("Invalid date");
				// get_Date();
			}

			return Date;
		} catch (Exception e) {
			throw new InvalidInputException("Invalid Input");
		}

	}

	/*
	 * Generate the new room id private String genRoomId(char roomType) { String
	 * genRoomId; do { Random rnd = new Random(); int id = 100 + rnd.nextInt(900);
	 * genRoomId = roomType + "_" + id; } while (containId(genRoomId)); return
	 * genRoomId; }
	 */

	/*
	 * check if roomid is in database
	 */ public boolean containId(String room_Id) {
		boolean contain = false;
		if (room_No != 0) {
			for (int i = 0; i < room_No; i++) {

				if (All_Id[i].equalsIgnoreCase(room_Id) && !(All_Id[i].equals(null))) {
					contain = true;
					return contain;

				} else {
					contain = false;
				}

			}
		}
		// TODO Auto-generated method stub
		return contain;

	}

	// to identify the room type based on room id
	private String getRoomTypeById(String roomId) {
		if (roomId.startsWith("S")) {
			return HOTEL_FANCY_CONSTANTS.SUITE;
		} else {
			return HOTEL_FANCY_CONSTANTS.STANDARD;
		}
	}

	/*
	 * This method will check and assign number of beds to the rooms
	 */ public int count_Bed(String room_Type, int bed_Count) throws InvalidBedCountException {

		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			System.out.print("In count_Bed()");

			if (!validateBedCount(bed_Count)) {
				throw new InvalidBedCountException("Standard room can have either 1,2 or 4 beds");
				/*
				 * System.out.println("Invalid Bedcount"); count_Bed(room_Type);
				 */
			}

		} else {
			this.bed_Count = 6;
		}
		return bed_Count;
	}

	// to validate bed count for given room type
	private boolean validateBedCount(int bedCount) {
		return (bedCount == 1 || bedCount == 2 || bedCount == 4);
	}

//Display all rooms with their data
	@SuppressWarnings("unused")
	private void DisplayAllRooms() {
		for (int i = 0; i < room_No; i++) {
			String Details = room[i].getDetails();
			System.out.println(Details);
		}
		// displayMenu();
	}

	public String getRoomDetails() {
		StringBuilder roomData = new StringBuilder();
		for (int i = 0; i < room_No; i++) {

			roomData.append(room[i].roomDetails());
			roomData.append(System.getProperty("line.separator"));
			for (int j = 0; j < room[i].getNoHired(); j++) {

				roomData.append(room[i].hiring[j].display());

				roomData.append(System.getProperty("line.separator"));
			}
		}

		return roomData.toString();
	}

	public void createImported_room(String room_Id, String room_Type, int bed_Count, DateTime last_Maintenance_Date,
			String features, String ImagePath, String status)
			throws ClassNotFoundException, SQLException, DatabaseException, InvalidInputException {
		dml = new DmlOperations();
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			this.room[room_No] = new Standard_Room(bed_Count, room_Id, room_Type, features, last_Maintenance_Date,
					ImagePath, status);

			dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date, ImagePath);
			if (!containId(room_Id)) {
				this.All_Id[room_No] = room_Id;
			}
			System.out.println("Room Created");
			this.room[room_No].display();

			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				this.room_No++;

			}

		} else {
			this.room[room_No] = new Suite(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);
			if (!containId(room_Id)) {
				this.All_Id[room_No] = room_Id;
			}

			dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status, last_Maintenance_Date, ImagePath);
			System.out.println("Room Created");
			this.room[room_No].display();
			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		}

	}

	public void createDatabaseImported_room(String room_Id, String room_Type, int bed_Count,
			DateTime last_Maintenance_Date, String features, String ImagePath, String status)
			throws ClassNotFoundException, SQLException, DatabaseException {
		dml = new DmlOperations();
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			this.room[room_No] = new Standard_Room(bed_Count, room_Id, room_Type, features, last_Maintenance_Date,
					ImagePath, status);

			// dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status,
			// last_Maintenance_Date, ImagePath);

			this.All_Id[room_No] = room_Id;

			System.out.println("Room Created " + this.All_Id[room_No]);

			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				this.room_No++;
			}

		} else {
			this.room[room_No] = new Suite(bed_Count, room_Id, room_Type, features, last_Maintenance_Date, ImagePath,
					status);

			this.All_Id[room_No] = room_Id;

			// dml.insert_Room_Entries(room_Id, bed_Count, features, room_Type, status,
			// last_Maintenance_Date, ImagePath);
			System.out.println("Room Created " + this.All_Id[room_No]);
			if (room_No == HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT) {
				System.out.println("Max Room Added");
			} else {
				room_No++;
			}

		}

	}

	public void setSuiteRoom(String setRoomId, String setlastMaintenanceDate, String setFeatures, String imagePath)
			throws SQLException, ClassNotFoundException, DatabaseException, InvalidInputException,
			FileNotFoundException, InvalidFileException, InvalidBedCountException, RoomNotAvailableException {
		for (int i = 0; i < room_No; i++) {
			if (All_Id[i] == null) {
				i--;
			}
			room_No = i;
		}

		for (int j = 0; j < room_No; j++) {
			System.out.println(All_Id[j]);
		}

		// String room_Type = HOTEL_FANCY_CONSTANTS.SUITE;
		this.status = HOTEL_FANCY_CONSTANTS.AVAILABLE;
		this.room_Type = getRoomTypeById(setRoomId);
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.STANDARD)) {
			throw new InvalidInputException("Only Adds Suite");
		}
		System.out.println(setRoomId);
		setRoomID(setRoomId);
		System.out.println("Room Type Created: " + this.room_Id + "Image Path: " + imagePath);
		this.last_Maintenance_Date = get_Date(setlastMaintenanceDate);
		this.bed_Count = count_Bed(room_Type, HOTEL_FANCY_CONSTANTS.SUITE_ROOM_BEDCOUNT);
		// System.out.print("Enter last maintenance date as (dd/mm/yyyy):\t\t\t");
		// last_Maintenance_Date = get_Date();
		this.features = get_features(setFeatures);
		this.ImagePath = get_Image(imagePath);
		if (!containId(room_Id)) {
			this.All_Id[room_No] = room_Id;
		}
		addRoom();
		// this.room[room_No] = new Standard_Room(Integer.parseInt(bedCount), setRoomId,
		// room_Type, setFeatures, rentDate, imagePath,status);
		// dml= new DmlOperations();
		// dml.insert_Room_Entries(setRoomId, Integer.parseInt(bedCount), setFeatures,
		// room_Type, status, null, ImagePath);

		// System.out.println("Room Created");
		// this.room[room_No].display();

	}

	// public void setRentRoom()

	public void setStandardRoom(String setRoomId, String setFeatures, String imagePath, String bedCount)
			throws NumberFormatException, SQLException, ClassNotFoundException, DatabaseException,
			InvalidInputException, FileNotFoundException, InvalidFileException, InvalidBedCountException,
			RoomNotAvailableException {
		for (int i = 0; i < room_No; i++) {
			if (All_Id[i] == null) {
				i--;
			}
			room_No = i;
		}

		for (int j = 0; j < room_No; j++) {
			System.out.println(All_Id[j]);
		}
		// String room_Type = HOTEL_FANCY_CONSTANTS.STANDARD;
		this.status = HOTEL_FANCY_CONSTANTS.AVAILABLE;
		this.room_Type = getRoomTypeById(setRoomId);
		if (room_Type.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.SUITE)) {
			throw new InvalidInputException("Only Adds standard Room");
		}
		setRoomID(setRoomId);

		System.out.println("Room Type Created: " + this.room_Id + "Image PAth: " + imagePath);
		this.bed_Count = count_Bed(room_Type, Integer.parseInt(bedCount));
		// System.out.print("Enter last maintenance date as (dd/mm/yyyy):\t\t\t");
		// last_Maintenance_Date = get_Date();
		this.features = get_features(setFeatures);
		this.ImagePath = get_Image(imagePath);
		if (!containId(room_Id)) {
			this.All_Id[room_No] = room_Id;
		}
		addRoom();

	}

	public void setRentRoom(String roomId, String rentDate, String custId, String days) throws InvalidInputException,
			NumberFormatException, ClassNotFoundException, RoomNotAvailableException, SQLException, DatabaseException {
		getInRoomId(roomId);
		rentRoomBefore(roomId, rentDate, custId, days);

	}

	public void setReturnRoom(String roomId, String returnDate)
			throws InvalidInputException, ClassNotFoundException, InvalidBedCountException,
			IncorrectReturnDateException, RoomNotAvailableException, SQLException, DatabaseException {
		getInRoomId(roomId);
		returnRoomBefore(roomId, returnDate);

	}

	public void setPerformMaintenance(String roomId) throws InvalidInputException, ClassNotFoundException,
			RoomNotAvailableException, SQLException, DatabaseException {
		getInRoomId(roomId);
		performMaintenanceBefore(roomId);

	}

	public void setCompleteMaintenance(String roomId, String completeMaintenanceDate) throws InvalidInputException,
			RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		getInRoomId(roomId);
		// int[] inputDate =
		// Arrays.stream(completeMaintenanceDate.split("/")).mapToInt(Integer::parseInt).toArray();
		// DateTime dt = new DateTime(inputDate[0], inputDate[1], inputDate[2]);
		completeMaintenanceBefore(completeMaintenanceDate, roomId);

	}

}
