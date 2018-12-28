package com.ashburnere.hellojavafx.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.ashburnere.hellojavafx.PersonTableApp;
import com.ashburnere.hellojavafx.model.Person;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View-Controller for the person table.
 * 
 * @author Erik
 */
public class PersonTableController {

	@FXML
	private TextField filterField;
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private TableColumn<Person, LocalDate> birthdayColumn;
	@FXML
	private TableColumn<Person, Hyperlink> editColumn;

	private PersonTableApp mainApp;

	public PersonTableController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 * 
	 * Initializes the table columns and sets up sorting and filtering.
	 */
	@FXML
	private void initialize() {
		// 0. Initialize the columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		// firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());

		final DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		// Custom rendering of the table cell.
		birthdayColumn.setCellFactory(column -> {
			return new TableCell<Person, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(myDateFormatter.format(item));

						// Style all dates in March with a different color.
						if (item.getMonth() == Month.MARCH) {
							setTextFill(Color.CHOCOLATE);
							setStyle("-fx-background-color: yellow");
						} else {
							setTextFill(Color.BLACK);
							setStyle("");
						}
					}
				}
			};
		});

		editColumn.setCellValueFactory(
				cellData -> new SimpleObjectProperty<Hyperlink>(createEditPersonHyperlink(cellData.getValue())));
	}

	private Hyperlink createEditPersonHyperlink(Person person) {
		Hyperlink hyperlink = new Hyperlink("Edit");

		// hyperlink.setOnAction(event -> showDialog(person));
		hyperlink.setOnAction(event -> showPersonEditDialog(person));

		return hyperlink;
	}

	private static void showDialog(Person person) {
		System.out.println("Edit person: " + person);

		VBox vBox = new VBox(new Label(person.toString()));
		Scene scene2 = new Scene(vBox, 250, 150);

		Stage stage2 = new Stage();
		stage2.setX(50);
		stage2.setY(50);
		stage2.setScene(scene2);
		stage2.setTitle("Stage 2");
		stage2.show();
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user clicks
	 * OK, the changes are saved into the provided person object and true is
	 * returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	private boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PersonTableApp.class.getClassLoader().getResource("PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setMainApp(PersonTableApp mainApp) {
		this.mainApp = mainApp;

	}

	public void loadData() {
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Person> filteredData = new FilteredList<>(this.mainApp.getMasterData(), p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (person.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Person> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(personTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		personTable.setItems(sortedData);
	}
}