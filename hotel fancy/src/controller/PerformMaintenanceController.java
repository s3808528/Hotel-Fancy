package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.PerformMaintenance;

public class PerformMaintenanceController implements EventHandler<ActionEvent> {
	public PerformMaintenanceController() {
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		new PerformMaintenance();
		
	}
}
