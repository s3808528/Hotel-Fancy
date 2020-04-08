package model.Exceptions;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;
	String str1;
	public DatabaseException(String str2) {
		str1=str2;
	   }
	   public String toString(){ 
		return ("DatabaseException Occurred: "+str1) ;
	   }
}