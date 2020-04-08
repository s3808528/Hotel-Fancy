package controller;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import view.AddRoom;


public class AddRoomController implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		new AddRoom();
		
	}
	



}
