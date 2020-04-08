package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Exceptions.DatabaseException;
import model.Exceptions.IncorrectReturnDateException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.RoomNotAvailableException;
import model.database.DmlOperations;

public abstract class Room {
	private String RoomId;// Room Id for all rooms
	private int bed_Count;// Bed_count of the room
	private String Feature;// features of the room
	private String Status;// Available,rented or maintenance
	private DateTime last_Maintenance_Date;// last maintenance day of the room
	private DateTime Mantainence_Completion_Time;// Completion of the maintenance of the room
	private String RoomType;// Suite or standard
	private double perDayRent;// rent of the day
	private String customerId;// id of the customer
	private DateTime returnDate;// return date of the room
	private DateTime rentDate;// rent date of the room
	private DateTime Estimated_RentDate;// estimated return date
	private int numOfdays;// num of days to book the room
	Hiring_Record hiring[] = new Hiring_Record[10];// records of the room
	private int noHired; // number of rooms hired
	private String imagePath;
	Scanner sread = new Scanner(System.in);
	DmlOperations dml;
	// get the number of rooms hired
	public int getNoHired() {
		return noHired;
	}

	// keep count of the number of rooms hired
	public void setNoHired(int noHired) {
		this.noHired = noHired;
	}

	// get the room type
	public String getRoomType() {
		return RoomType;
	}

	// set the room type
	public void setRoomType(String roomType) {
		RoomType = roomType;
	}

	// constructor
	public Room(int bed_Count, String RoomId, String RoomType, String Feature, DateTime last_Maintenance_Date,
			String ImagePath, String status) {

		this.setBed_Count(bed_Count);
		this.RoomId = RoomId;
		this.Feature = Feature;
		this.last_Maintenance_Date = last_Maintenance_Date;
		this.RoomType = RoomType;
		this.setStatus(status);
		this.setImagePath(ImagePath);
		System.out.println("Image Room Path:"+this.getImagePath());

	}

	// get the id
	public String get_Id() {
		return this.RoomId;

	}

	// display the room details
	public void display() {
		System.out.print("\n\t\tRoom Details\t\t\r\n");
		System.out.print("\t\tRoom Id: " + this.RoomId);
		System.out.print("\n\t\tType of Room: " + this.RoomType);
		System.out.print("\n\t\tLast maintenance: " + this.last_Maintenance_Date);
		System.out.print("\n\t\tFeatures: " + this.Feature);
		System.out.print("\n\t\tStatus: " + this.Status + "\n");

	}

	// get the bed count
	public int getBed_Count() {
		return bed_Count;
	}

	// set the bed count
	public void setBed_Count(int bed_Count) {
		this.bed_Count = bed_Count;
	}

	// get the status
	public String getStatus() {
		return Status;
	}

	// set the status
	public void setStatus(String status) {
		Status = status;
	}

	/**
	 * @return the last_Maintenance_Date
	 */
	public DateTime getLast_Maintenance_Date() {
		return last_Maintenance_Date;
	}

	/**
	 * @param last_Maintenance_Date the last_Maintenance_Date to set
	 */
	public void setLast_Maintenance_Date(DateTime last_Maintenance_Date) {
		this.last_Maintenance_Date = last_Maintenance_Date;
	}

	/**
	 * @return the feature
	 */
	public String getFeature() {
		return Feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(String feature) {
		Feature = feature;
	}

	/*
	 * rent the particular room
	 */public void rent(String customerId, DateTime rentDate, int numOfRentDay)
			throws RoomNotAvailableException, ClassNotFoundException, SQLException, DatabaseException {

		// boolean isRented = false;

		this.setCustomerId(customerId);
		this.setRentDate(rentDate);
		this.rentDate=rentDate;

		if (this.Status.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.MAINTENANCE)) {
			throw new RoomNotAvailableException("Room is not available");
			// System.out.println("Sorry Room is at Maintenance");
		} else {
			this.Status = HOTEL_FANCY_CONSTANTS.RENTED;
			this.minDays(numOfRentDay);
			
			// isRented = true;
			this.Estimated_RentDate = Calc_Return_Date(rentDate, numOfRentDay);
			this.numOfdays = numOfRentDay;
			
			this.hiring[noHired] = new Hiring_Record();
			this.hiring[noHired].setRecord(this, "STILL RENTED");
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
			noHired++;
			System.out.println("DONE");

		}

	}

	/*
	 * return the room after completing the renting // $59 per day if the room has 1
	 * bed // $99 per day if the room has 2 beds // $199 per day if the rooms has 4
	 * beds
	 */
	public void returnRoom(DateTime returnDate) throws InvalidBedCountException, IncorrectReturnDateException,
			RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		// boolean isRoomReturned = false;
		//System.out.println(this.rentDate);
		//System.out.println(this.hiring[this.noHired].getRecordId());
		
		System.out.println(this.hiring[this.getNoHired()-1].getRent_Date());
		int diff = DateTime.diffDays(returnDate, this.hiring[noHired - 1].getRent_Date());
		System.out.println(returnDate);
		if (this.Status.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.MAINTENANCE)) {

			throw new RoomNotAvailableException("Room is at Maintenance ");
		} else if (this.Status.equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.AVAILABLE)) {
	
			throw new RoomNotAvailableException("Room is not rented ");
		} else if (diff < 0) {
			throw new IncorrectReturnDateException("Incorrect Return Date entered");
		} else {
			this.returnDate = returnDate;
			
			this.Status = HOTEL_FANCY_CONSTANTS.AVAILABLE;
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
			if(this.getNumOfdays()==0) {
				this.hiring[noHired - 1].setRecord(this,this.hiring[this.getNoHired()-1]);
			}
			else {
				this.hiring[noHired-1].setRecord(this);
			}
			System.out.println(this.returnDate+" "+this.rentDate);
			
			

			if (noHired == 10) {
				noHired = 0;

			}

		}
	}

	protected abstract double Calculate_Fee(DateTime returnDate2, double perDayRent2) throws InvalidBedCountException;

	// get the customer id
	public String getCustomerId() {
		return customerId;
	}

	// set the customer id
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	// get the return date
	public DateTime getRentDate() {
		return this.rentDate;
	}

	// set the rent date
	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	// calculate the return date
	private DateTime Calc_Return_Date(DateTime rentDate, int numOfRentDay) {
		// TODO Auto-generated method stub
		DateTime dt = new DateTime(rentDate, numOfRentDay);
		return dt;
	}

	// get the estimated return date
	public DateTime getEstimated_RentDate() {
		return Estimated_RentDate;
	}
	// overridden in suite and standard room

	protected abstract void minDays(int days);
	// overridden in suite and standard room

	protected abstract double get_Rental_Fee(int bed_Count) throws InvalidBedCountException;

	// overridden in suite and standard room

	protected abstract double Calc_Rental_Fee(int numOfRentDay, double perDayRent) throws InvalidBedCountException;

	// overridden in suite and standard room
	protected abstract void completeMaintenance(DateTime completionDate) throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException;

	// get the rent per day
	public double getPerDayRent() {
		return perDayRent;
	}

	// set the rent per day
	public void setPerDayRent(double perDayRent) {
		this.perDayRent = perDayRent;
	}

	// get the return date
	public DateTime getReturnDate() {
		return returnDate;
	}

	// set the return date
	public void setReturnDate(DateTime returnDate) {
		this.returnDate = returnDate;
	}

	// get the number of days
	public int getNumOfdays() {
		return numOfdays;
	}

	// set the number of days
	public void setNumOfdays(int numOfdays) {
		this.numOfdays = numOfdays;
	}

	// overridden in suite and standard room
	public abstract String getDetails();

	// overridden in suite and standard room
	public abstract void performMaintenance()
			throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException;

	// get the maintenance completion time
	public DateTime getMantainence_Completion_Time() {
		return Mantainence_Completion_Time;
	}

	public abstract String roomDetails();

	// set the mantainence completion time
	public void setMantainence_Completion_Time(DateTime mantainence_Completion_Time) {
		Mantainence_Completion_Time = mantainence_Completion_Time;
	}

	// overridden in suite and standard room
	public abstract String toString();

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void updateNoHired() {
		this.noHired++;

	}

	protected ArrayList<Hiring_Record> getHiringRecords() {
		return null;

	}

	/*
	 * Set records of hiring for each room when importing a file
	 */ public void setRecords(String roomId, String custId, Hiring_Record hr)
			throws ClassNotFoundException, InvalidBedCountException, SQLException, DatabaseException {
		
		this.hiring[this.getNoHired()]=new Hiring_Record();
		this.hiring[this.getNoHired()].setInitialRecords(hr,roomId);
		System.out.println("RentDate"+this.hiring[this.getNoHired()].getRent_Date());
		this.noHired++;
		System.out.println("Rent Date:" + hr.getRent_Date());
		// this.hiring[this.getNoHired()].setRent_Date(hr.getRent_Date());;
		System.out.println("setRecord");
		if (noHired == 10) {
			noHired = 0;
		}
	}

}
