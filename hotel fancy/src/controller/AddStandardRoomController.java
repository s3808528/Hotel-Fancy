package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.AddStandardRoom;

public class AddStandardRoomController implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		new AddStandardRoom();
		
		
	}

}
