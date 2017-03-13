package es.hol.piotrkrzyminski.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import es.hol.piotrkrzyminski.connection.Connect;
import es.hol.piotrkrzyminski.model.Czytelnik;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
	private Connect connect = new Connect();
	/**
	 * elementy do dodawania nowych czytelników
	 */
	@FXML
	private TextField firstNameTextField; //pobieranie imienia
	@FXML
	private TextField secondNameTextField; //pobieranie drugiego imienia
	@FXML
	private TextField surnameTextField; //pobieranie nazwiska
	@FXML
	private TextField peselTextField; //pobieranie pesela
	@FXML
	private DatePicker birthDateDatePicker; // pobieranie daty
	@FXML
	private Button addButton; // przycisk dodaj¹cy dane z pól tekstowych do bazy danych
	@FXML
	private Button clearButton; //przycisk do wyczyszczenia pól
	@FXML
	private Label infoLabel; //pole tekstowe informuj¹ce o tym, czy dane zosta³y wype³nione w poprawny sposób
	@FXML
	private Label connectionInfoLabel; // pole tekstowe informuj¹ce o stanie po³¹czenia z baz¹ danych
	@FXML
	private Button deleteCzytelnikButton; //przycisk usuwania rekordu z bazy danych i tabeli
	
	@FXML
	private TableView<Czytelnik> czytelnicyTableView;  //Tabela z danymi pobranymi z bazy danych
	@FXML
	private TableColumn<Czytelnik, String> czytelnicyFirstNameTableColumn; //kolumna Imiê
	@FXML
	private TableColumn<Czytelnik, String> czytelnicySecondNameTableColumn; //kolumna Drugie imiê
	@FXML
	private TableColumn<Czytelnik, String> czytelnicySurnameTableColumn; //kolumna Nazwisko
	@FXML
	private TableColumn<Czytelnik, String> czytelnicyPeselTableColumn; //kolumna pesel
	@FXML
	private TableColumn<Czytelnik, LocalDate> czytelnicyBirthDateTableColumn; //kolumna data urodzenia
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connect.connect(); // odpowiada za wczytanie sterownika i po³aczenie z baz¹ danych
		
		/**
		 * wypisz w polu tekstowym odpowiedni¹ informacjê czy aplikacja po³¹czy³a siê poprawnie z baz¹ danych
		 */
		if(connect.isConnected()) {
			connectionInfoLabel.setText("Po³¹czono");
			connectionInfoLabel.setTextFill(Color.GREEN);
		}
		else {
			connectionInfoLabel.setText("Nie po³¹czono");
			connectionInfoLabel.setTextFill(Color.RED);
		}
		
		fillTable();
	}
	
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
		 * Stwórz obiekt nowego czytelnika i dodaj go do bazy danych
		 */
		
		if(firstName!=null && surname!=null && pesel!=null && birthDate!=null) {
			Czytelnik czytelnik = new Czytelnik(firstName, secondName, surname, pesel, birthDate);
			
			connect.insertCzytelnik(czytelnik);
			
			infoLabel.setText(czytelnik.getMessage());
			czytelnik=null;
			fillTable();
		}	
	}
	
	/**
	 * akcja, która odpowiada za usuniêcie aktualnie zaznaczonego wiersza z bazy danych i odœwie¿enie zawartoœci tabeli
	 * @param event
	 */
	@FXML
	private void deleteCzytelnikButtonOnAction(ActionEvent event) {
		Czytelnik selectedCzytelnik = czytelnicyTableView.getSelectionModel().getSelectedItem();
		if(selectedCzytelnik!= null) {
			connect.deleteCzytelnik(selectedCzytelnik.getPesel());
			fillTable();
		}
	}
	
	@FXML
	private void clearButtonOnAction(ActionEvent event) {
		firstNameTextField.setText(null);
		secondNameTextField.setText(null);
		surnameTextField.setText(null);
		peselTextField.setText(null);
		birthDateDatePicker.getEditor().clear();
	}
	
	/**
	 * metoda odpowiadaj¹ca za wype³nienie tabeli czytelnikami zapisanymi w bazie danych
	 */
	private void fillTable() {
		/**
		 * pobierz z bazy danych kolekcjê czytelników zapisanych w bazie danych
		 */
		ObservableList<Czytelnik> czytelnicy = connect.selectCzytelnicy();
		
		/**
		 * Przypisz odpowiednie dane do kolumn tabeli
		 */
		czytelnicyTableView.setItems(czytelnicy);
		czytelnicyFirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		czytelnicySecondNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
		czytelnicySurnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
		czytelnicyPeselTableColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
		czytelnicyBirthDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
	}
}
