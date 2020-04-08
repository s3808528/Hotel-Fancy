package model;

import java.sql.SQLException;

import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.RoomNotAvailableException;
import model.database.DmlOperations;

public class Suite extends Room {
	DmlOperations dml;
	public Suite(int bed_count, String RoomId, String Feature, String Room_type, DateTime last_Maintenance_time,String ImagePath,String status) {
		super(bed_count=HOTEL_FANCY_CONSTANTS.SUITE_ROOM_BEDCOUNT, RoomId, Feature, Room_type, last_Maintenance_time,ImagePath,status);

	}

	/*
	 * validate the minimum days
	 */public void minDays(int Days) {
		DateTime next_maintainance_Date = new DateTime(this.getLast_Maintenance_Date(),
				HOTEL_FANCY_CONSTANTS.SUITE_MAINTENANCE);
		int diffOfCur_NextMantainenceDate = DateTime.diffDays(next_maintainance_Date, this.getRentDate());
		if (Days > diffOfCur_NextMantainenceDate && Days <= 0) {
			System.out.println("\nSorry Suite " + this.get_Id() + " Cannot be rented at the Moment.");
			this.setStatus(HOTEL_FANCY_CONSTANTS.AVAILABLE);
		}
	}

	/*
	 * get the rental fee per day
	 */public double get_Rental_Fee(int bed_Count) {
		// TODO Auto-generated method stub
		int diff = DateTime.diffDays(this.getReturnDate(), this.getRentDate());
		if (diff <= this.getNumOfdays() && bed_Count == 6) {
			this.setPerDayRent(999.0);
		} else if (diff > this.getNumOfdays() && bed_Count == 6) {
			this.setPerDayRent(1099.0);
		} else {
			System.out.println("Suite Must have bed_Count 6");
			System.exit(0);
		}
		return this.getPerDayRent();
	}

	@Override
	/*
	 * Calculate rental fee for Suite class
	 */protected double Calc_Rental_Fee(int numOfRentDay, double perDayRent) {
		int diff = DateTime.diffDays(this.getReturnDate(), this.getRentDate());
		double rental_Fee = this.get_Rental_Fee(this.getBed_Count());
		if (diff <= numOfRentDay) {

			rental_Fee = numOfRentDay * this.getPerDayRent();

		} else if (diff > numOfRentDay) {
			rental_Fee = this.getPerDayRent() * diff;// rental rate of each late day is 135%

		}

		return rental_Fee;
	}

	/*
	 * This Method is used to calculate the rental fees
	 */protected double Calculate_Fee(DateTime returnDate2, double perDayRent2) throws InvalidBedCountException {
		int diff = DateTime.diffDays(this.getReturnDate(), this.getRentDate());
		double rental_Fee = this.get_Rental_Fee(this.getBed_Count());
		int numOfRentDay=DateTime.diffDays(this.getEstimated_RentDate(), this.getRentDate());
		if (diff <= numOfRentDay) {

			rental_Fee = numOfRentDay * this.getPerDayRent();

		} else if (diff > numOfRentDay) {
			rental_Fee = this.getPerDayRent() * diff;// rental rate of each late day is 135%

		}

		return rental_Fee;
		
	}
	@Override
	//get the details of the room
	public String getDetails() {
		String Details = "Room ID:\t" + this.get_Id() + "\nNumber of beds:\t " + this.getBed_Count() + "\nType:\t"
				+ this.getRoomType() + "\nStatus:\t" + this.getStatus() + "\nLast maintenance date:\t"
				+ this.getLast_Maintenance_Date() + "\nFeature summary:" + this.getFeature();

		if (this.getNoHired() != 0 || this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)) {
			for (int i = this.getNoHired()-1; i>=0; i--) {
				Details += "\n-------------------------------------------------------------\n"+"\nRENTAL RECORD:\n" + this.hiring[i].display();
				
			}

		} else {
			Details += "\n-------------------------------------------------------------\n"+"\nRENTAL RECORD: \n empty";
		}
		return Details;

	}
	
	public String roomDetails() {
		String Details = this.toString();
		return Details;
	}

	@Override
	//perform the maintenance
	public void performMaintenance() throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {

		//boolean perfomedMaintenance = false;
		if (this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)
				|| this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.MAINTENANCE)) {
			throw new RoomNotAvailableException("Cannot Perform Mantainence");
			//System.out.println("Cannot Perform Mantainence");
		} else {
			this.setStatus(HOTEL_FANCY_CONSTANTS.MAINTENANCE);
			System.out.println("Suite " + this.get_Id() + " is now under Mantainance.");
			System.out.println("DML DONE");
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
			System.out.println("DML DONE");
			//perfomedMaintenance = true;
		}
		//return perfomedMaintenance;
	}

	@Override
	//complete the maintenance
	protected void completeMaintenance(DateTime completionDate) throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		@SuppressWarnings("unused")
		boolean completedMaintenance = false;
		int diff=DateTime.diffDays(completionDate, this.getLast_Maintenance_Date());
		if(this.getReturnDate()!=null) {
			int diff_Completion=DateTime.diffDays(this.getReturnDate(), completionDate);
			if(diff_Completion>0){
				throw new RoomNotAvailableException("Room is in Mantainence");
				//System.out.println("Sorry the room is in mantainence cannot complete maintenance after that");
			}
		}
		if (this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)
				|| this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.AVAILABLE)) {
			throw new RoomNotAvailableException("Cannot Perform Mantainence");
			//System.out.println("Cannot Perform Mantainence");
		} else if(diff>10 || diff<10){
			throw new RoomNotAvailableException("Cannot delay Mantainence of suite for more/less than 10 days");
			//System.out.println("Cannot delay Mantainence of suite for more/less than 10 days");
		}else {
			this.setStatus(HOTEL_FANCY_CONSTANTS.AVAILABLE);
			
			completedMaintenance = true;
			this.setLast_Maintenance_Date(completionDate);
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
			dml.updateLastMaintenanceDate(this.get_Id(),this.getLast_Maintenance_Date());
		}
		// TODO Auto-generated method stub
		//return completedMaintenance;

	}
	//overriding the tostring method for suite
	public String toString() {
		
		return String.format("%s:%d:%s:%s:%s:%s", this.get_Id(), this.getBed_Count(), this.getFeature(),this.getRoomType(), this.getStatus(),this.getLast_Maintenance_Date(),this.getImagePath()
				);
}



}
