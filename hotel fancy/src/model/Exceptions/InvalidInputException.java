package model.Exceptions;

public class InvalidInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String str1;
	   public InvalidInputException(String str2) {
			str1=str2;
		   }
		   public String toString(){ 
			return ("InvalidInputException Occurred: "+str1) ;
		   }
		
	}

