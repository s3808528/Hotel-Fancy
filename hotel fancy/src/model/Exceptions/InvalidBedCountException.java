package model.Exceptions;



public class InvalidBedCountException extends Exception {

	private static final long serialVersionUID = 1L;
	String str1;
	public InvalidBedCountException(String str2) {
		str1=str2;
	   }
	   public String toString(){ 
		return ("InvalidBedCountException Occurred: "+str1) ;
	   }
}
