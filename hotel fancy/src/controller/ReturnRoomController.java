package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.ReturnRoom;

public class ReturnRoomController implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent arg0) {
		new ReturnRoom();
	}

}
