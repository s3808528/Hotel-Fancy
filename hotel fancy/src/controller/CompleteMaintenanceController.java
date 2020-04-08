package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.CompleteMaintenance;

public class CompleteMaintenanceController implements EventHandler<ActionEvent> {
public CompleteMaintenanceController() {

}

@Override
public void handle(ActionEvent arg0) {
	new CompleteMaintenance();
	
}
}
