package model.fileHandling;
import java.io.*;

//import constants.FileConstants;
import model.CityLodge;
import model.Exceptions.ImportExportException;

public class Export_File {
	public Export_File() {}
	
	//used to export room data to file
	public static void ExportFile(String filePath,CityLodge cl) throws IOException, ImportExportException {
		filePath = filePath+"/Exported_data.txt";
		FileWriter fileWriter = new FileWriter(filePath);
		try {
			
			String room_Data = cl.getRoomDetails();
		fileWriter.write(room_Data);
            fileWriter.close();
		} catch (FileNotFoundException e) {
			throw new ImportExportException("File not exported");
	
		}catch(Exception e) {
			throw new ImportExportException("File not exported");
		} finally {
			fileWriter.close();
		}
	}
}
