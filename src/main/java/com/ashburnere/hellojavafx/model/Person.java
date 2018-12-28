package com.ashburnere.hellojavafx.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Simple model class for the person table.
 * 
 * @author Erik
 */
public class Person {

	private IntegerProperty id;
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final ObjectProperty<LocalDate> birthday;

	public Person(Integer id, String firstName, String lastName, LocalDate birthday) {
		this.id = new SimpleIntegerProperty(id);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName=" + lastName + '}';
	}
}