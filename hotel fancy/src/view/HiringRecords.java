package view;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import controller.AddRoomController;
import controller.CompleteMaintenanceController;
import controller.MainController;
import controller.PerformMaintenanceController;
import controller.RentRoomController;
import controller.ReturnRoomController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.CityLodge;
import model.HOTEL_FANCY_CONSTANTS;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidBedCountException;
import model.Exceptions.InvalidFileException;
import model.Exceptions.InvalidInputException;
import model.database.DatabaseConnection;
import model.fileHandling.Export_File;
import model.fileHandling.Import_File;

public class HiringRecords {
	protected static Connection conn;
	private static Statement statement;
	private static Statement statement1;
	@SuppressWarnings("unused")
	private ObservableList<String> data = FXCollections.observableArrayList();
	private static CityLodge cl = new CityLodge();
	int i = 0;
	int j = 0;
	@SuppressWarnings({ "rawtypes", "unused" })
	private TableView table;
	@SuppressWarnings({ "unused", "rawtypes" })
	private ObservableList roomData;
	@SuppressWarnings("unused")
	private Text actionStatus;
	private static BorderPane border_Pane = new BorderPane();
	BorderPane detailsBp = new BorderPane();

	@SuppressWarnings("unused")
	public HiringRecords(String roomId) throws DatabaseException, ClassNotFoundException, SQLException {

		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
		if (conn != null) {
			statement = conn.createStatement();
			statement1 = conn.createStatement();
		} else {
			throw new DatabaseException("Connection to DB failed");
		}
		String rooms = String.format("Select DISTINCT * from %s Where Room_ID='%s'", HOTEL_FANCY_CONSTANTS.TABLE_1_NAME,
				roomId);
		String hiringRecords = String.format("SELECT  * FROM %s Where Room_ID ='%s'",
				HOTEL_FANCY_CONSTANTS.TABLE_2_NAME, roomId);

		@SuppressWarnings("unused")
		ResultSet roomsSet = statement.executeQuery(rooms);
		ResultSet hiringSet = statement1.executeQuery(hiringRecords);
		@SuppressWarnings("unused")
		ImageView imageView = new ImageView();
		ArrayList<String> HiringRecords = new ArrayList<String>();
		while (hiringSet.next()) {
			System.out.println("hiring records");
			System.out.println(hiringSet.getString("Record_ID"));

			/*
			 * + "(Record_ID varchar(100), Est_Return_Date varchar(15), Rent_Date
			 * varchar(15), Actual_Return_Date varchar(15), Rental_Fee float, Late_Fee
			 * float, " + "Room_ID varchar(10)
			 */ HiringRecords.add("Record Id: " + hiringSet.getString("Record_ID") + "\nRent_Date: "
					+ hiringSet.getString("Rent_Date") + "\nEstimated Return Date: "
					+ hiringSet.getString("Est_Return_Date") + "\nActual Return Date: "
					+ hiringSet.getString("Actual_Return_Date") + "\nRental Fee: " + hiringSet.getString("Rental_Fee")
					+ "\nLate_Fee" + hiringSet.getString("Late_Fee"));
		}

		Stage st = new Stage();
		Button button = new Button();
		VBox headContainer = new VBox();
		st.setTitle("Hotel Fancy");
		Button menuItem1 = new Button("Import List");
		Button menuItem2 = new Button("Export List");
		Button menuItem3 = new Button("Quit");
		border_Pane.setPadding(new Insets(5));
		HBox head = new HBox();
		head.getChildren().addAll(menuItem1, menuItem2, menuItem3);

		//
		// border_Pane.setCenter(createLeftLayout());

		Label title = new Label("Hotel Fancy");
		title.setFont(Font.font("poppins", FontWeight.BOLD, 50));
		title.setTextFill(Color.web("#bb22cc"));

		title.setTextAlignment(TextAlignment.CENTER);
		headContainer.getChildren().addAll(title, head);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		menuItem1.setOnAction(new EventHandler<ActionEvent>() {

			// vBox.getChildren().addAll(addRoom,);
			@Override
			public void handle(ActionEvent event) {

				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
				fileChooser.setInitialDirectory(new File("../hotel fancy/src/"));
				File selectedFile = fileChooser.showOpenDialog(st);
				if (selectedFile != null) {
					try {
						Import_File im = new Import_File();
						im.import_File(selectedFile.getPath(),
								cl);

					} catch (Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("File Not found");
						alert.setContentText("Couldnot import!");
					}
				}
			}
		});
		menuItem2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				DirectoryChooser dir = new DirectoryChooser();
				String selectedDirectory = null;
				dir.setInitialDirectory(new File("../hotel fancy/src/"));

				File file = dir.showDialog(st);
				String directory;
				directory = file.getPath();
				if (file != null) {
					try {
						Export_File ex = new Export_File();
						Export_File.ExportFile(
								directory, cl);
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("FILE EXPORTED");
						alert2.setContentText("File:"+file);

					} catch (Exception e) {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Information Dialog");
						alert2.setHeaderText("File Not found");
						alert2.setContentText("Couldnot export!");
						// throw new ImportExportException("Could not export!");
					}
				}
				

			}

		});
		MainController mainControllerEvent=new MainController();
		
		menuItem3.setOnMouseClicked((new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				st.hide();
				st.getScene().getRoot();

			}
		}));

		border_Pane.setTop(headContainer);

		AddRoomController addRoomEvent = new AddRoomController();
		RentRoomController rentRoomEvent = new RentRoomController();
		ReturnRoomController returnRoomEvent = new ReturnRoomController();
		PerformMaintenanceController performMaintenanceEvent = new PerformMaintenanceController();
		CompleteMaintenanceController completeMaintenanceEvent = new CompleteMaintenanceController();
		VBox vBox = new VBox();
		Button addRoom = new Button("Add Room");
		Button rentRoom = new Button("Rent Room");
		Button returnRoom = new Button("returnRoom");
		Button completeMaintenance = new Button("Complete Maintenance");
		Button performMaintenance = new Button("Perform Maintenance");
		vBox.getChildren().addAll(addRoom, rentRoom, returnRoom, completeMaintenance, performMaintenance);
		border_Pane.setLeft(vBox);

		addRoom.setOnAction(addRoomEvent);
		rentRoom.setOnAction(rentRoomEvent);
		returnRoom.setOnAction(returnRoomEvent);
		completeMaintenance.setOnAction(completeMaintenanceEvent);
		performMaintenance.setOnAction(performMaintenanceEvent);
		ObservableList<String> Roomitems = FXCollections.observableArrayList();
		ListView<String> listView = new ListView<String>();
		// TableView tableView = new TableView();
		// vb.getChildren().addAll(roomInfo,details);
		Iterator<String> iter = HiringRecords.iterator();
		listView.getItems().addAll(HiringRecords);
//		TableColumn<Image, Label, Button> column1 = new TableColumn<Image, Label, Button>(); // //
		// tableView.getColumns().add(column1);
		listView.setCellFactory(param -> new ListCell<String>() {

			public void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);
				if (empty) {
					setText(null);

					setGraphic(null);
				} else {
					setText(name);
				} // setGraphic(hb);
					// setGraphic(imageView); }
					// Scene mainScene = new Scene(border_Pane, 1200, 700);

			}
		}); // setText(name);
		border_Pane.setCenter(listView);
		Scene mainScene = new Scene(border_Pane, 1200, 700);
		st.setScene(mainScene);
		st.show();
	}
}
