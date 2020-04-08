package model;

import java.sql.SQLException;

import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.RoomNotAvailableException;
import model.database.DmlOperations;

public class Standard_Room extends Room {

	public Standard_Room(int bed_count, String RoomId, String Feature, String Room_type,
			DateTime last_Maintenance_time, String ImagePath,String status) {
		super(bed_count, RoomId, Feature, Room_type, last_Maintenance_time, ImagePath,status);

	}

	/*
	 * Check if minimum days are needed
	 */public void minDays(int Days) {

		String day = this.getRentDate().getNameOfDay();

		if (day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Saturday")) {
			if (Days < 2 || Days > 10) {
				System.out.println("Sorry Cannot rent room for less than 2 days or more than 10 days");
				this.setStatus(HOTEL_FANCY_CONSTANTS.AVAILABLE);
			}

		} else {
			if (Days < 3 || Days > 10) {
				System.out.println("Sorry Cannot rent room for less than 3 days or more than 10 days");
				this.setStatus(HOTEL_FANCY_CONSTANTS.AVAILABLE);
			}
		}
	}

	//get the rental fee for per day
	 public double get_Rental_Fee(int bed_Count) throws InvalidBedCountException {
		// TODO Auto-generated method stub
		if (bed_Count == 1) {
			this.setPerDayRent(59.0);
		} else if (bed_Count == 2) {
			this.setPerDayRent(99.0);
		} else if (bed_Count == 4) {
			this.setPerDayRent(199.0);
		}
		else {
			 throw new InvalidBedCountException("Invalid Bed Count");
		}
		return this.getPerDayRent();
	}

	
	//calculate the rental fee
	protected double Calc_Rental_Fee(int numOfRentDay, double perDayRent) throws InvalidBedCountException {
		int diff = DateTime.diffDays(this.getReturnDate(), this.getRentDate());
		System.out.println("Return Date " + this.getReturnDate() + " RentDate " + this.getRentDate());
		double rental_Fee = this.get_Rental_Fee(this.getBed_Count());
		if (diff <= numOfRentDay) {

			rental_Fee = numOfRentDay * this.getPerDayRent();

		} else if (diff > numOfRentDay) {
			rental_Fee = 1.35 * this.getPerDayRent() * diff;// rental rate of each late day is 135%

		}

		return rental_Fee;
	}
	//get the details of the room
	public String getDetails() {

		String Details = "\n-------------------------------------------------------------\n" + "\t\tRoom ID:\t"
				+ this.get_Id() + "\n\t\tNumber of beds:\t " + this.getBed_Count() + "\nt\\tType:\t"
				+ this.getRoomType() + "\nt\\tStatus:\t" + this.getStatus() + "\nt\\tFeature summary:"
				+ this.getFeature();
		if (this.getNoHired() != 0 || this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)) {
			for (int i = this.getNoHired() - 1; i >= 0; i--) {

				Details += "\n-------------------------------------------------------------\n" + "\nRENTAL RECORD:\n"
						+ this.hiring[i].display();

			}

		} else {
			Details += "\n-------------------------------------------------------------\n"
					+ "\nRENTAL RECORD: \n empty";
		}
		return Details;

	}

	public String roomDetails() {
		
		String Details = this.toString();
		return Details;
		
	}
	
	// perform maintenance on the standard_room
	public void performMaintenance() throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		//boolean perfomedMaintenance = false;
		if (this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)
				|| this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.MAINTENANCE)) {
			throw new RoomNotAvailableException("Cannot Perform Mantainence");
			//System.out.println("Cannot Perform Mantainence");
		} else {
			this.setStatus(HOTEL_FANCY_CONSTANTS.MAINTENANCE);
			System.out.println("Standard Room " + this.get_Id() + " is now under Mantainance.");
			dml=new DmlOperations();
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
		//	perfomedMaintenance = true;
		}
		// TODO Auto-generated method stub
	//	return perfomedMaintenance;
	}

	@Override
	//complete the maintenance
	protected void completeMaintenance(DateTime completionDate) throws RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		//boolean completedMaintenance = false;

		if (this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.RENTED)
				|| this.getStatus().equalsIgnoreCase(HOTEL_FANCY_CONSTANTS.AVAILABLE)) {
			throw new RoomNotAvailableException("Cannot Complete Mantainence");
			//System.out.println("Cannot Complete Mantainence");

		} else {
			this.setStatus(HOTEL_FANCY_CONSTANTS.AVAILABLE);
			System.out.println("in complete maintenance"+this.get_Id()+ this.getStatus()+completionDate);
			//completedMaintenance = true;
			this.setLast_Maintenance_Date(completionDate);
			dml=new DmlOperations();
			dml.updateRoomStatus(this.get_Id(), this.getStatus());
			dml.updateLastMaintenanceDate(this.get_Id(),completionDate);
		}
		// TODO Auto-generated method stub
		//return completedMaintenance;
	}
	//overriding the toString for standard room
	public String toString() {

		return String.format("%s:%d:%s:%s:%s", this.get_Id(), this.getBed_Count(), this.getFeature(),this.getRoomType(), this.getStatus(),this.getImagePath()
				);

	}

	/*
	 * This method is used to calculate the rental fee
	 */@Override
	protected double Calculate_Fee(DateTime returnDate2, double perDayRent2) throws InvalidBedCountException {
		int diff = DateTime.diffDays(returnDate2, this.getRentDate());
		System.out.println("Return Date " + this.getReturnDate() + " RentDate " + this.getRentDate());
		double rental_Fee = this.get_Rental_Fee(this.getBed_Count());
		

			rental_Fee = diff * this.getPerDayRent();

		

		return rental_Fee;
		
	}
}
