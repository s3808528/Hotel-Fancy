package model.Exceptions;

public class InvalidFileException extends Exception {

	private static final long serialVersionUID = 1L;
	String str1;
	public InvalidFileException(String str2) {
		str1=str2;
	   }
	   public String toString(){ 
		return ("InvalidFileException Occurred: "+str1) ;
	   }
}
