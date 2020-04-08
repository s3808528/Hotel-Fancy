package model.Exceptions;

public class ImportExportException extends Exception {
	private static final long serialVersionUID = 1L;
	String str1;

	public ImportExportException(String str2) {
			str1=str2;
		   }

	public String toString() {
		return ("ImportExportException Occurred: " + str1);
	}
}
