package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.RentRoom;

public class RentRoomController implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent arg0) {
		new RentRoom();
		
	}

}
