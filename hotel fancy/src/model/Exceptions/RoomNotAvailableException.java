package model.Exceptions;

public class RoomNotAvailableException extends Exception {
	private static final long serialVersionUID = 1L;
	String str1;

public RoomNotAvailableException(String str2) {
			str1=str2;
		   }

	public String toString() {
		return ("RoomNotAvailableException Occurred: " + str1);
	}

}
