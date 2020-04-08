package view;

import java.sql.SQLException;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidInputException;
import model.Exceptions.RoomNotAvailableException;

public class PerformMaintenance {
public PerformMaintenance() {
	Stage st = new Stage();

	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	// Room ID
	Label roomIdLabel = new Label("Room Id (S_xxx) : ");
	gridPane.add(roomIdLabel, 0, 1);
	TextField setRoomId = new TextField();
	setRoomId.setPrefHeight(60);

	gridPane.add(setRoomId, 1, 1);
	Button submit = new Button("Submit");
	submit.setPrefHeight(40);
	submit.setDefaultButton(true);
	submit.setPrefWidth(400);
	gridPane.add(submit, 0, 6, 4, 1);
	submit.setOnMouseClicked((new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
		try {
			Main.setPerformMaintenance(setRoomId.getText());
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Information Dialog");
			alert2.setHeaderText("Submited");
			alert2.setContentText("Room Id " + setRoomId.getText()+" is now under Maintenance");
			alert2.show();
		} catch (ClassNotFoundException e) {
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
		} catch (RoomNotAvailableException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Exception");
			alert.setContentText("Exception" + e);
			//e.printStackTrace();
			alert.show();
		} catch (SQLException e) {
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
		}catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Exception");
			alert.setContentText("Exception" + e);
			//e.printStackTrace();
			alert.show();
		}
		st.close();
			
		}	
		
}));Scene mainScene = new Scene(gridPane, 1200, 700);
st.setScene(mainScene);
st.show();
}
}
