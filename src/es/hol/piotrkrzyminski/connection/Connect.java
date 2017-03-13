/**
 * Klasa odpowiada za wszelkie operacje zwi¹zane z bazami danych:
 * wczytanie sterownika, po³¹czenie z baz¹ danych, utworzenie tabeli (je¿eli nie istnieje), dodawanie, usuwanie i pobieranie rekordów z bazy
 */

package es.hol.piotrkrzyminski.connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import es.hol.piotrkrzyminski.model.Czytelnik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Connect {
	
	private static Connection connection;
	private static Statement statement;
	
	/**
	 * Wczytaj sterownik bazy danych sqlite, otwórz po³¹czenie z baz¹ danych wywo³aj metodê createTable()
	 */
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC"); //wczytanie sterownika do aplikacji
		} catch (ClassNotFoundException exception) { 
			exception.printStackTrace(); //tworzenie po³¹czenia z baz¹ danych
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:biblioteka.db"); //po³¹czenie z baz¹ danych
			statement = connection.createStatement();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		createTable();
	}
	
	/**
	 * odpowiada za utworzenie tabeli czytelnicy, je¿eli jeszcze nie istnieje
	 */
	public void createTable() {
		String createCzytelnicy = "CREATE TABLE IF NOT EXISTS czytelnicy (pesel VARCHAR(11) NOT NULL PRIMARY KEY, firstName VARCHAR(50) NOT NULL, "
				+ "secondName VARCHAR(50), surname VARCHAR(50) NOT NULL, birthDate DATE NOT NULL)";
		
		try {
			statement.execute(createCzytelnicy);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * metoda odpowiadaj¹ca za dodanie do bazy danych nowego obiektu czytelnik, przes³anego jako argument
	 * @param czytelnik
	 */
	public void insertCzytelnik(Czytelnik czytelnik) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into czytelnicy (firstName,secondName,surname,pesel,birthDate) "
					+ "values (?,?,?,?,?);");	
			preparedStatement.setString(1, czytelnik.getFirstName());
			preparedStatement.setString(2, czytelnik.getSecondName());
			preparedStatement.setString(3, czytelnik.getSurname());
			preparedStatement.setString(4, czytelnik.getPesel());
			preparedStatement.setDate(5, Date.valueOf(czytelnik.getBirthDate()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metoda pobiera z bazy danych wszystkie rekordy i przypisuje je do listy czytelnicy, któr¹ na koniec zwraca
	 * @return
	 */
	public ObservableList<Czytelnik> selectCzytelnicy() {
		ObservableList<Czytelnik> czytelnicy = FXCollections.observableArrayList();
		
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM czytelnicy");
			
			String firstName, secondName, surname, pesel;
			LocalDate birthDate;

				while(resultSet.next()) {
					firstName = resultSet.getString("firstName");
					secondName = resultSet.getString("secondName");
					surname = resultSet.getString("surname");
					pesel = resultSet.getString("pesel");
					birthDate = resultSet.getDate("birthDate").toLocalDate();
					czytelnicy.add(new Czytelnik(firstName, secondName, surname, pesel, birthDate));
				} 
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		
		return czytelnicy;
	}
	
	/**
	 * usuwa rekord w bazie danych na podstawie przes³anego przez argument numery pesel
	 * @param pesel
	 */
	public void deleteCzytelnik(String pesel) {
		String deleteSQL = "DELETE FROM czytelnicy WHERE pesel="+pesel+";";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sprawdza stan po³¹czenia z baz¹ danych i zwraca prawdê jeœli program jest po³¹czony, lub fa³sz jeœli nie jest po³aczony
	 * @return
	 */
	public boolean isConnected() {
		try {
			if(!connection.isClosed())
				return true;
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}
}
