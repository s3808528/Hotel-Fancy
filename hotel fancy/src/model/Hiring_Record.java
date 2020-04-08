package model;


import java.sql.SQLException;


import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;

import model.database.DmlOperations;

public class Hiring_Record  {
	DmlOperations dml;

	private String Record_Id; // concatenating roomId_ + customerId_ + rentDate (8 digit format: ddmmyyyy)
	private DateTime Rent_Date;// the date when a customer rents the room
	

	private double Rental_Fee;// the fee calculated based on the type of room, the rent date and the estimated
								// return date.
	private double Late_Fee;// the additional fee which must be calculated when the actual return date is
							// after the estimated return date
	private DateTime Estimated_Return_Date; // the calculated date given the number of days a customer wants to rent the
											// room and the rent date shown above
	private DateTime Actual_Return_Date; // the date when the customer actually returns the room

	public Hiring_Record(DateTime rentDate, String Record_ID, DateTime estimatedReturnDate, DateTime actualReturnDate,
			Double Rental_Fee, Double Late_Fee) throws ClassNotFoundException, SQLException, DatabaseException {
		this.Record_Id = Record_ID;
		this.Rent_Date = rentDate;
		this.Estimated_Return_Date = estimatedReturnDate;
		this.Actual_Return_Date = actualReturnDate;
		this.Rental_Fee = Rental_Fee;
		this.Late_Fee = Late_Fee;
	}

	public Hiring_Record() throws ClassNotFoundException, SQLException, DatabaseException {
		// super();
		this.Record_Id = null;
		this.Rent_Date = null;
		this.Estimated_Return_Date = null;
		this.Actual_Return_Date = null;
		this.Rental_Fee = 0.0;
		this.Late_Fee = 0.0;
		
	}



	/*
	 * Create the record id
	 */public String Create_Record_Id(String roomId, String customerId, DateTime rentDate) {
		return this.Record_Id = roomId + "_" + customerId + "_" + rentDate.getEightDigitDate();
	}

	/*
	 * Set the record for the rooms
	 */ public void setRecord(Room room,Hiring_Record hr) throws InvalidBedCountException, SQLException, ClassNotFoundException, DatabaseException {
		
		this.Record_Id =hr.getRecord_Id();
		this.Rent_Date = hr.getRent_Date();
		this.Estimated_Return_Date = hr.getEstimated_Return_Date();
		this.Actual_Return_Date = room.getReturnDate();
		System.out.println(room.get_Rental_Fee(room.getBed_Count()));
		System.out.println(room.getReturnDate());
		this.Rental_Fee = room.Calculate_Fee(room.getReturnDate(), room.get_Rental_Fee(room.getBed_Count()));
		this.setLate_Fee(room.get_Rental_Fee(room.getBed_Count()));
		String current_State = hr.toString();
		System.out.println("current_State:\t" + current_State);
		dml=new DmlOperations();
		dml.updateHiringRecordDetails(this.getRecord_Id(), this.Actual_Return_Date.toString(), this.Rental_Fee, this.getLate_Fee());
		
		/*
		 * System.out.println("Record id "+this.Record_Id+"\nRental_Fee "+this.
		 * Rental_Fee+"\nEstimated_Return_Date "+room.getEstimated_RentDate());
		 */ }

	/* overloading */
	public void setRecord(Room room, String Before) throws SQLException, ClassNotFoundException, DatabaseException {

		this.Record_Id = Create_Record_Id(room.get_Id(), room.getCustomerId(), room.getRentDate());
		this.Rent_Date = room.getRentDate();
		this.Estimated_Return_Date = room.getEstimated_RentDate();
		String current_State = this.toString();
		
		System.out.println("current_State:\t" + current_State);
		
		dml=new DmlOperations();
		dml.insertRecordEntries(this.Record_Id, this.Rent_Date.toString(), this.Estimated_Return_Date.toString(),
				this.Estimated_Return_Date.toString(),0.0,0.0,room.get_Id());
		System.out.println("Rented");
		/*
		 * System.out.println("Record id "+this.Record_Id+"\nRental_Fee "+this.
		 * Rental_Fee+"\nEstimated_Return_Date "+room.getEstimated_RentDate());
		 */ }

	/*
	 * Display the data
	 */public String display() {
		String Details;
		if (this.Actual_Return_Date == null) {

			Details =   this.Record_Id + ":" + this.Rent_Date + ":"
					+ this.Estimated_Return_Date;
		} else {

			Details = this.Record_Id + ":" + this.Rent_Date + ":"
					+ this.Estimated_Return_Date + ":" + this.Actual_Return_Date + ":"
					+ this.Rental_Fee+":"+this.Late_Fee;
		}
		return Details;
	}

	/*
	 * returning late fee
	 */public double getLate_Fee() {
		return Late_Fee;
	}

	/*
	 * setting Late fee
	 */ public void setLate_Fee(double late_Fee) {
		Late_Fee = late_Fee;
	}

	// overridden to string for hiring record
	public String toString() {
		if (this.Actual_Return_Date == null) {
			return String.format("%s:%s:%s:none:none:none", this.Record_Id, this.Rent_Date, this.Estimated_Return_Date);
		} else {
			return String.format("%s:%s:%s:%s:%.2f:%.2f", this.Record_Id, this.Rent_Date, this.Estimated_Return_Date,
					this.Actual_Return_Date, this.Rental_Fee, this.Late_Fee);
		}
	}

	/*
	 * set Initial Records when imported
	 */
	public void setInitialRecords(Hiring_Record hr,Room room) throws InvalidBedCountException, SQLException, ClassNotFoundException, DatabaseException {
		this.Record_Id = hr.getRecord_Id();
		this.Rent_Date = hr.getRent_Date();
		this.Estimated_Return_Date = hr.getEstimated_Return_Date();
		this.Actual_Return_Date = hr.getActual_Return_Date();
		this.Rental_Fee = hr.getRental_Fee();
		this.setLate_Fee(hr.getLate_Fee());
		String current_State = this.toString();
		System.out.println("current_State:\t" + current_State);
		dml=new DmlOperations();
		System.out.println(hr.Record_Id+ hr.Rent_Date.toString()+ hr.Estimated_Return_Date.toString()+ hr.Actual_Return_Date.toString()+
				this.Rental_Fee+ hr.getLate_Fee()+ room.get_Id());
//		dml.insertRecordEntries(hr.Record_Id, hr.Rent_Date.toString(), hr.Estimated_Return_Date.toString(), hr.Actual_Return_Date.toString(),
//				this.Rental_Fee, hr.getLate_Fee(), room.get_Id());
	
		
	}

	public void setInitialRecords(Hiring_Record hr,String roomID) throws InvalidBedCountException, SQLException, ClassNotFoundException, DatabaseException {
	//	System.out.println(this.getRecord_Id());
		
		  this.Record_Id = hr.getRecord_Id(); 
		  this.Rent_Date = hr.getRent_Date();
		  this.Estimated_Return_Date = hr.getEstimated_Return_Date();
		  this.Actual_Return_Date =hr.getActual_Return_Date(); 
		  this.Rental_Fee = hr.getRental_Fee();
		  this.setLate_Fee(hr.getLate_Fee()); 
		  String current_State = hr.toString(); 
		  System.out.println("current_State:\t" + current_State);
		  dml=new DmlOperations();
		  dml.insertRecordEntries(this.Record_Id, this.Rent_Date.toString(), this.Estimated_Return_Date.toString(), this.Actual_Return_Date.toString(),
					this.Rental_Fee, this.getLate_Fee(), roomID);
		  System.out.println("hiring record"+this.Rent_Date);
		 
	}

	public String getRecordId() {
		return Record_Id;
	}
	public String getRecord_Id() {
		return Record_Id;
	}

	public void setRecord_Id(String record_Id) {
		Record_Id = record_Id;
	}

	public DateTime getRent_Date() {
		return Rent_Date;
	}

	public void setRent_Date(DateTime rent_Date) {
		Rent_Date = rent_Date;
	}

	public double getRental_Fee() {
		return Rental_Fee;
	}

	public void setRental_Fee(double rental_Fee) {
		Rental_Fee = rental_Fee;
	}

	public DateTime getEstimated_Return_Date() {
		return Estimated_Return_Date;
	}

	public void setEstimated_Return_Date(DateTime estimated_Return_Date) {
		Estimated_Return_Date = estimated_Return_Date;
	}

	public DateTime getActual_Return_Date() {
		return Actual_Return_Date;
	}

	public void setActual_Return_Date(DateTime actual_Return_Date) {
		Actual_Return_Date = actual_Return_Date;
	}

	public void setRecord(Room room) throws InvalidBedCountException, ClassNotFoundException, SQLException, DatabaseException {
		this.Record_Id =this.Create_Record_Id(room.get_Id(), room.getCustomerId(), room.getRentDate());
		this.Rent_Date = room.getRentDate();
		this.Estimated_Return_Date = room.getEstimated_RentDate();
		this.Actual_Return_Date = room.getReturnDate();
		
		this.Rental_Fee = room.Calc_Rental_Fee(room.getNumOfdays(), room.getPerDayRent());
		this.setLate_Fee(room.get_Rental_Fee(room.getBed_Count()));
		String current_State = this.toString();
		System.out.println("current_State:\t" + current_State);
		dml=new DmlOperations();
		dml.updateHiringRecordDetails(this.getRecord_Id(), this.Actual_Return_Date.toString(), this.Rental_Fee, this.getLate_Fee());
		
		
	}
}
