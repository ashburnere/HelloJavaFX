package com.ashburnere.hellojavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Hello World example for JavaFX from
 * http://tutorials.jenkov.com/javafx/your-first-javafx-application.html
 * 
 * Java FX application lifecycle is launch => init => start => stop
 * 
 * @author Erik
 *
 */
public class HelloJavaFX extends Application {

	@Override
	public void init() {
		System.out.println("init");
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println("start");
		primaryStage.setTitle("My First JavaFX App");

		Label label = new Label("Hello World, JavaFX !");
		Scene scene = new Scene(label, 400, 200);
		primaryStage.setScene(scene);

		primaryStage.show();

		VBox vBox = new VBox(new Label("Label 2"));
		Scene scene2 = new Scene(vBox, 250, 150);

		Stage stage2 = new Stage();
		stage2.setX(50);
		stage2.setY(50);
		stage2.setScene(scene2);
		stage2.setTitle("Stage 2");
		stage2.show();
	}

	@Override
	public void stop() {
		System.out.println("stop");
	}

	public static void main(String[] parameters) {
		launch(parameters);
	}

}