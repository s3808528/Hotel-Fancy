package model.Exceptions;

public class IncorrectReturnDateException extends Exception {

	private static final long serialVersionUID = 1L;
	String str1;

	public IncorrectReturnDateException(String str2) {
			str1=str2;
		   }

	public String toString() {
		return ("IncorrectReturnDateException Occurred: " + str1);
	}

}
