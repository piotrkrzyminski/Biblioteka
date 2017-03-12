package es.hol.piotrkrzyminski.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import es.hol.piotrkrzyminski.connection.Connect;
import es.hol.piotrkrzyminski.model.Czytelnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
	private Czytelnik czytelnik = new Czytelnik();
	private Connect connect = new Connect();
	/**
	 * elementy do dodawania nowych czytelników
	 */
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField secondNameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private TextField peselTextField;
	@FXML
	private DatePicker birthDateDatePicker;
	@FXML
	private Button addButton;
	@FXML
	private Label infoLabel;
	@FXML
	private Label connectionInfoLabel;
	
	@FXML
	private void addButtonOnAction(ActionEvent event) {
		/**
		 * Pobierz dane z pól tekstowych i przypisz je do zmiennych
		 */
		String firstName = firstNameTextField.getText();
		String secondName = secondNameTextField.getText();
		String surname = surnameTextField.getText();
		String pesel = peselTextField.getText();
		LocalDate birthDate = birthDateDatePicker.getValue();
		
		/**
		 * Wyœlij pobrane dane do Czytelnicy.java
		 */
		czytelnik.setFirstName(firstName);
		czytelnik.setSecondName(secondName);
		czytelnik.setSurname(surname);
		czytelnik.setPesel(pesel);
		czytelnik.setBirthDate(birthDate);
		
		czytelnik.addToDatabase();
		
		infoLabel.setText(czytelnik.getMessage());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connect.connect();
		
		if(connect.isConnected()) {
			connectionInfoLabel.setText("Po³¹czono");
			connectionInfoLabel.setTextFill(Color.GREEN);
		}
		else {
			connectionInfoLabel.setText("Nie po³¹czono");
			connectionInfoLabel.setTextFill(Color.RED);
		}
	}
}
