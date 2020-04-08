package view;

import controller.AddStandardRoomController;
import controller.AddSuiteRoomController;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AddRoom  {


	public AddRoom() {
		Stage addRoomStage1=new Stage();
		BorderPane bp=new BorderPane();
		Button back=new Button("Back");
		HBox header=new HBox();
		Label title = new Label("Hotel Fancy");
		title.setFont(Font.font("poppins", FontWeight.BOLD, 50));
		title.setTextFill(Color.web("#bb22cc"));
		
		title.setTextAlignment(TextAlignment.CENTER);;
		header.getChildren().addAll(title,back);
		back.setOnAction(e -> addRoomStage1.close());
		AddSuiteRoomController AddSuite =new AddSuiteRoomController();
		AddStandardRoomController AddStandard =new AddStandardRoomController();
		bp.setTop(header);
		Button addSuiteRoom=new Button("Add Suite");
		Button addStandardRoom=new Button("Add Standard");
	addSuiteRoom.setOnAction(AddSuite);
	addStandardRoom.setOnAction(AddStandard);
	
		HBox middle=new HBox();
		middle.getChildren().addAll(addSuiteRoom,addStandardRoom);
		bp.setCenter(middle);
	 	Scene mainScene = new Scene(bp, 1200, 700);
		addRoomStage1.setScene(mainScene);
		addRoomStage1.show();
	//	addRoomStage1.setRoot(primaryStage);
		
	}


}
