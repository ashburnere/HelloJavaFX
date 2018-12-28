package com.ashburnere.hellojavafx;

import java.io.IOException;
import java.time.LocalDate;

import com.ashburnere.hellojavafx.model.Person;
import com.ashburnere.hellojavafx.view.PersonTableController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Hello World example for JavaFX from
 * https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering
 * https://code.makery.ch/library/javafx-tutorial/part3/
 * 
 * 
 * @author Erik
 *
 */
public class PersonTableApp extends Application {

	private Stage primaryStage;

	private final ObservableList<Person> masterData = FXCollections.observableArrayList();

	private void initData() {
		this.masterData.add(new Person(1, "Hans", "Muster", LocalDate.of(1999, 12, 4)));
		this.masterData.add(new Person(2, "Ruth", "Mueller", LocalDate.of(2014, 4, 22)));
		this.masterData.add(new Person(3, "Heinz", "Kurz", LocalDate.of(2000, 12, 4)));
		this.masterData.add(new Person(4, "Cornelia", "Meier", LocalDate.of(1989, 1, 1)));
		this.masterData.add(new Person(5, "Werner", "Meyer", LocalDate.of(1972, 3, 5)));
		this.masterData.add(new Person(6, "Lydia", "Kunz", LocalDate.of(1970, 1, 1)));
		this.masterData.add(new Person(7, "Anna", "Best", LocalDate.of(1982, 3, 2)));
		this.masterData.add(new Person(8, "Stefan", "Meier", LocalDate.of(1893, 8, 29)));
		this.masterData.add(new Person(9, "Martin", "Mueller", LocalDate.of(1977, 5, 24)));
	}

	@Override
	public void start(Stage primaryStage) {
		this.initData();
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Sorting and Filtering");

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PersonTable.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Give the controller access to the main app
			PersonTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.loadData();

			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public ObservableList<Person> getMasterData() {
		return this.masterData;
	}

	public static void main(String[] args) {
		launch(args);
	}
}