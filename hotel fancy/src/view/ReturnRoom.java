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

public class ReturnRoom {
public ReturnRoom() {
	Stage st = new Stage();

	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	// Room ID
	
	Label roomIdLabel = new Label("Return Room Id (R/S_xxx) : ");
	gridPane.add(roomIdLabel, 0, 1);
	TextField setRoomId = new TextField();
	setRoomId.setPrefHeight(60);
	gridPane.add(setRoomId, 1, 1);
	
	Label returnDateLabel = new Label("Return Date : ");
	gridPane.add(returnDateLabel, 0, 2);
	TextField setReturnDate = new TextField();
	setReturnDate.setPrefHeight(60);
	gridPane.add(setReturnDate, 1, 2);
	
	
	Button submit = new Button("Submit");
	submit.setPrefHeight(40);
	submit.setDefaultButton(true);
	submit.setPrefWidth(400);
	gridPane.add(submit, 0, 6, 4, 1);
	submit.setOnMouseClicked((new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			try {
				
				Main.setReturnRoom(setRoomId.getText(),setReturnDate.getText());
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Information Dialog");
			alert2.setHeaderText("Submited");
			alert2.setContentText("Room Returned" + setRoomId.getText()  );
			alert2.show();
				/*
				 * ButtonType yes = new ButtonType("Yes",ButtonData.OK_DONE); ButtonType no =
				 * new ButtonType("NO", ButtonData.CANCEL_CLOSE); Alert alert = new
				 * Alert(AlertType.WARNING,
				 * "You have to pay for the previous stated number of days.", yes, no);
				 * Optional<ButtonType> result = alert.showAndWait(); if(result.orElse(yes) !=
				 * null) {
				 * 
				 * }
				 */

				
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
			} catch (InvalidInputException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Exception");
				alert.setContentText("Exception" + e);
				//e.printStackTrace();
				alert.show();
			} catch (Exception e) {
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
