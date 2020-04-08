package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;


import model.fileHandling.*;
import controller.AddRoomController;
import controller.CompleteMaintenanceController;

import controller.PerformMaintenanceController;
import controller.RentRoomController;
import controller.ReturnRoomController;
import javafx.application.Application;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.stage.FileChooser.ExtensionFilter;
import model.CityLodge;
import model.HOTEL_FANCY_CONSTANTS;
import model.Exceptions.*;
import model.database.*;

public class Main extends Application {
	protected static Connection conn;
	private static Statement statement;
	private static Statement statement1;
	private ObservableList<String> data = FXCollections.observableArrayList();
	private static CityLodge cl = new CityLodge();
	int i = 0;
	int j = 0;
	@SuppressWarnings("rawtypes")
	private TableView table;
	@SuppressWarnings({ "unused", "rawtypes" })
	private ObservableList roomData;
	@SuppressWarnings("rawtypes")
	private TableColumn Details;
	private static BorderPane border_Pane = new BorderPane();
	BorderPane detailsBp = new BorderPane();
	Text text;

	private ArrayList<String> roomIds = new ArrayList<String>();
	

	// private String[]roomImages=new String[HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT];
//	String[] roomIds=new String[HOTEL_FANCY_CONSTANTS.MAX_ROOM_COUNT];

	public static void main(String[] args) throws SQLException, FileNotFoundException, ClassNotFoundException,
			DatabaseException, InvalidBedCountException {

		CreateTable ct = new CreateTable();
		ct.create_Table();

//		ImportFile im = new ImportFile();
//		im.import_File("../hotel fancy/src/sample_data.txt", cl);

		@SuppressWarnings("unused")
		DatabaseConnection dbConnection = new DatabaseConnection(cl);
		DmlOperations dml = new DmlOperations();
		dml.getRoomEntries(cl);

		// cl.runApp();
		launch(args);

	}

	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button button = new Button();
		VBox headContainer = new VBox();
		primaryStage.setTitle("Hotel Fancy");
		Button menuItem1 = new Button("Import List");
		Button menuItem2 = new Button("Export List");
		Button menuItem3 = new Button("Quit");
		border_Pane.setPadding(new Insets(20));
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
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
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

				File file = dir.showDialog(primaryStage);
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
						// throw new ImportExportException("Couldnot export!");
					}
				}
				

			}

		});
		menuItem3.setOnAction(e -> primaryStage.close());

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
		Button performMaintenance = new Button("Perform Maintenance");
		Button completeMaintenance = new Button("Complete Maintenance");
		
		vBox.getChildren().addAll(addRoom, rentRoom, returnRoom, performMaintenance, completeMaintenance);
		border_Pane.setLeft(vBox);

		addRoom.setOnAction(addRoomEvent);
		rentRoom.setOnAction(rentRoomEvent);
		returnRoom.setOnAction(returnRoomEvent);
		performMaintenance.setOnAction(performMaintenanceEvent);
		completeMaintenance.setOnAction(completeMaintenanceEvent);
		displayAllRooms();

		Scene mainScene = new Scene(border_Pane, 1200, 700);
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ObservableList getInitialTableData() throws SQLException, ClassNotFoundException, DatabaseException {

		conn = DatabaseConnection.getConnection(HOTEL_FANCY_CONSTANTS.DATABASE_NAME);
		if (conn != null) {
			statement = conn.createStatement();
			statement1 = conn.createStatement();
		} else {
			throw new DatabaseException("Connection to DB failed");
		}
		String rooms = String.format("Select DISTINCT * from %s", HOTEL_FANCY_CONSTANTS.TABLE_1_NAME);
		String hiringRecords = String.format("SELECT DISTINCT * FROM %s", HOTEL_FANCY_CONSTANTS.TABLE_2_NAME);

		ResultSet roomsSet = statement.executeQuery(rooms);
		ResultSet hiringSet = statement1.executeQuery(hiringRecords);
		@SuppressWarnings("unused")
		ImageView imageView = new ImageView();

		while (hiringSet.next()) {
			System.out.println("hiring");
			System.out.println(hiringSet.getString("Record_ID"));

		}

		
		List list = new ArrayList();
		while (roomsSet.next()) {
			System.out.println("hiring");

			roomIds.add("Room Id: " + roomsSet.getString("Room_ID") + "\n Status: " + roomsSet.getString("Status")
					+ "\n Bed Count: " + roomsSet.getString("Bed_Count") + "\nFeatures: "
					+ roomsSet.getString("Features"));
			list.add(new DispRoom(roomsSet.getString("Room_ID"), roomsSet.getString("Status"),
					roomsSet.getString("Bed_Count"), roomsSet.getString("Features"), roomsSet.getString("Image_Path")));

		}
		ObservableList data1 = FXCollections.observableList(list);

		return data1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void displayAllRooms()
			throws ClassNotFoundException, SQLException, DatabaseException, FileNotFoundException {

		table = new TableView();
		roomData = getInitialTableData();
		
		TableColumn Image = new TableColumn("Image");
		Image.setCellValueFactory(new PropertyValueFactory("image_file"));
		Details = new TableColumn("Summary");
		Details.setCellValueFactory(new PropertyValueFactory("Summary"));
		data = getInitialTableData();
		table.setItems(data);
		table.getColumns().setAll(Image, Details);
		addButtonToTable();

		border_Pane.setCenter(table);

	}

	/*
	 * This Class is used to add the button in the table
	 */@SuppressWarnings("unchecked")
	private void addButtonToTable() {
		@SuppressWarnings("rawtypes")
		TableColumn<String, Void> colBtn = new TableColumn("More Details");

		Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
			@Override
			public TableCell<String, Void> call(final TableColumn<String, Void> param) {
				final TableCell<String, Void> cell = new TableCell<String, Void>() {

					private final Button btn = new Button("Action");

					{
						btn.setOnAction((ActionEvent event) -> {

							text = new Text();

							DispRoom pb = new DispRoom();
							table.getSelectionModel().select(getIndex());
							pb = (DispRoom) table.getSelectionModel().getSelectedItem();
							text.setText(pb.toString());
							//System.out.println(text.getText());
							try {
								new HiringRecords(text.getText());
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
							} catch (SQLException e) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Exception");
								alert.setContentText("Exception" + e);
							//	e.printStackTrace();
								alert.show();
							}catch (Exception e) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Exception");
								alert.setContentText("Exception" + e);
							//	e.printStackTrace();
								alert.show();
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		table.getColumns().add(colBtn);

	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void setTableappearance() {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefWidth(600);
		table.setPrefHeight(600);
	}

	public static void setSuiteRoom(String setRoomId, String setlastMaintenanceDate, String setFeatures,
			String imagePath) throws SQLException, ClassNotFoundException, DatabaseException, FileNotFoundException,
			InvalidInputException, InvalidFileException, InvalidBedCountException, RoomNotAvailableException {
		// cl.setSuiteRoom(setRoomId,setlastMaintenanceDate,setFeatures);
		cl.setSuiteRoom(setRoomId, setlastMaintenanceDate, setFeatures, imagePath);

	}

	public static void setstandardRoom(String setRoomId, String setFeatures, String imagePath, String bedCount)
			throws NumberFormatException, ClassNotFoundException, SQLException, DatabaseException,
			FileNotFoundException, InvalidInputException, InvalidFileException, InvalidBedCountException,
			RoomNotAvailableException {
		cl.setStandardRoom(setRoomId, setFeatures, imagePath, bedCount);
	}

	public static void setRentRoom(String roomId, String custId, String rentDate, String days)
			throws NumberFormatException, ClassNotFoundException, InvalidInputException, RoomNotAvailableException,
			SQLException, DatabaseException {
		cl.setRentRoom(roomId, rentDate, custId, days);

	}

	/*
	 * This Class id used to set the table view in the main GUI
	 */public static class DispRoom {

		private SimpleStringProperty Type;
		private SimpleStringProperty Status;
		private SimpleStringProperty Features;
		private SimpleStringProperty ID;
		private SimpleStringProperty bedCount;
		private SimpleStringProperty image_file;
		@SuppressWarnings("unused")
		private String Summary;

		public DispRoom() {
		}
	 
		public DispRoom(String Id, String status, String bedCount, String features, String imgpath) {
			ID = new SimpleStringProperty(Id);
			Status = new SimpleStringProperty(status);
			this.bedCount = new SimpleStringProperty(bedCount);
			Features = new SimpleStringProperty(features);
			image_file = new SimpleStringProperty(imgpath);
			Summary = getSummary();
		}

		public String getSummary() {
			return "Room Id: " + ID.get() + "\nStatus: " + Status.get() + "\nFeatures : " + Features.get()
					+ "\nBed Count : " + bedCount.get();
		}

		public String getType() {
			return Type.get();
		}

		public String getStatus() {
			return Status.get();
		}

		public String getFeatures() {
			return Features.get();
		}

		public String getID() {
			return ID.get();
		}

		public String getbedCount() {
			return bedCount.get();
		}

		public String toString() {
			return ID.get();
		}

		public ImageView getImage_file() throws FileNotFoundException {
			return new ImageView(new Image(new FileInputStream(image_file.get()), 200, 200, false, false));

		}

	}

	public static void setReturnRoom(String roomId, String returnDate)
			throws ClassNotFoundException, InvalidInputException, InvalidBedCountException,
			IncorrectReturnDateException, RoomNotAvailableException, SQLException, DatabaseException {
		cl.setReturnRoom(roomId, returnDate);

	}

	public static void setPerformMaintenance(String roomId) throws ClassNotFoundException, InvalidInputException,
			RoomNotAvailableException, SQLException, DatabaseException {
		cl.setPerformMaintenance(roomId);

	}

	public static void CompleteMaintenance(String roomId, String completeMaintenanceDate)
			throws InvalidInputException, RoomNotAvailableException, SQLException, ClassNotFoundException, DatabaseException {
		cl.setCompleteMaintenance(roomId, completeMaintenanceDate);

	}
}
