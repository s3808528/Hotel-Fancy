package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import view.AddSuiteRoom;


public class AddSuiteRoomController implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		new AddSuiteRoom();

	}

}
