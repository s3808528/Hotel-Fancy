package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import controller.setDataController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.InvalidFileException;
import model.Exceptions.InvalidInputException;

public class AddStandardRoom  {
	
	private String setRoomId;

	private String setImagePath;
	private int numberBeds;



	public int getNumberBeds() {
		return numberBeds;
	}



	public void setNumberBeds(int numberBeds) {
		this.numberBeds = numberBeds;
	}



	private String setFeatures;
	
	public String getSetImagePath() {
		return setImagePath;
	}



	public void setSetImagePath(String setImagePath) {
		this.setImagePath = setImagePath;
	}
	public String getSetRoomId() {
		return setRoomId;
	}
	


	public String getSetFeatures() {
		return setFeatures;
	}
	

	public void setSetFeatures(String setFeatures) {
		this.setFeatures = setFeatures;
	}



public AddStandardRoom() {

		Stage st=new Stage();
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		// Room ID
		Label roomIdLabel = new Label("Room Id (R_xxx) : ");
		gridPane.add(roomIdLabel, 0, 1);
		TextField setRoomId = new TextField();
		setRoomId.setPrefHeight(40);
		
		gridPane.add(setRoomId, 1, 1);
		Label numberBeds=new Label("Number Of Beds : ");
		gridPane.add(numberBeds, 0, 2);
		TextField setNumberBeds = new TextField();
		setRoomId.setPrefHeight(60);
				
		gridPane.add(setNumberBeds,1,2);
		
		Label Features=new Label("Features : ");
		gridPane.add(Features, 0, 3);
		TextField setFeatures=new TextField();
		setFeatures.setPrefHeight(40);
		
		gridPane.add(setFeatures, 1, 3);
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setInitialDirectory(new File("../hotel fancy/Images"));
	    Button button = new Button("Select File");
	    Label label1 = new Label("No Image Selected");
	    setImagePath="../hotel fancy/Images/No_image_available.jpg";
	    button.setOnAction(e -> {
	        File selectedFile = fileChooser.showOpenDialog(st);
	        
	        
	        if (selectedFile != null) {
	       	 
	            label1.setText("File selected: " + selectedFile.getPath());
	            setImagePath=selectedFile.getPath();
	        }
	        else {
	            label1.setText("Default image selected");
	        }
	    });
	    gridPane.add(label1,0, 4);
	    gridPane.add(button, 1, 4);
		

		@SuppressWarnings("unused")
		setDataController sdc=new setDataController();
		// Add Submit Button
		Button submit = new Button("Submit");
		submit.setPrefHeight(50);
		submit.setDefaultButton(true);
		submit.setPrefWidth(400);
		gridPane.add(submit, 0,5 , 3, 1);
		   submit.setOnMouseClicked((new EventHandler<MouseEvent>() { 
		         public void handle(MouseEvent event) { 
		        	 try {Main.setstandardRoom(setRoomId.getText(), setFeatures.getText(),setImagePath,setNumberBeds.getText());
		        	 Alert alert2 = new Alert(AlertType.INFORMATION);
		        		alert2.setTitle("Information Dialog");
		        		alert2.setHeaderText("Submited");
		        						
		        		alert2.show();
		        		
		        		
							
						} catch (SQLException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
							
						} catch (ClassNotFoundException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						} catch (DatabaseException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						} catch (FileNotFoundException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						} catch (InvalidInputException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						} catch (InvalidFileException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						} catch (InvalidBedCountException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						}
						catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Exception");
							alert.setContentText("Exception" + e);
							//e.printStackTrace();
							alert.show();
						}
		        		st.close();
		             
		         } 
		      })); 
		
	   	Scene mainScene = new Scene(gridPane, 1200, 700);
		st.setScene(mainScene);
		st.show();
	
}

}
