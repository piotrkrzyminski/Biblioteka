package es.hol.piotrkrzyminski.connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Connect {
	
	private static Connection connection;
	private static Statement statement;
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC"); //wczytanie sterownika do aplikacji
			System.out.println("Wczytano sterownik");
		} catch (ClassNotFoundException exception) {
			System.out.println("Brak sterownika JDBC"); //tworzenie po³¹czenia z baz¹ danych
			exception.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:biblioteka.db"); //po³¹czenie z baz¹ danych
			statement = connection.createStatement();
			System.out.println("Otwarto polaczenie");
		} catch (SQLException exception) {
			System.out.println("Problem z otwarciem polaczenia");
			exception.printStackTrace();
		}
		
		createTables();
	}
	
	public boolean createTables() {
		String createCzytelnicy = "CREATE TABLE IF NOT EXISTS czytelnicy (id INTEGER PRIMARY KEY AUTOINCREMENT, firstName VARCHAR(50) NOT NULL, "
				+ "secondName VARCHAR(50), surname VARCHAR(50) NOT NULL, pesel VARCHAR(11) NOT NULL, birthDate DATE NOT NULL)";
		
		try {
			statement.execute(createCzytelnicy);
			System.out.println("Utworzono tebelê czytelnicy.");
		} catch (SQLException exception) {
			System.out.println("Nie utworzono tabeli czytelnicy.");
			exception.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertCzytelnik(String firstName, String secondName, String surname, String pesel, LocalDate birthDate) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into czytelnicy values (NULL,?,?,?,?,?);");	
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, secondName);
			preparedStatement.setString(3, surname);
			preparedStatement.setString(4, pesel);
			preparedStatement.setDate(5, Date.valueOf(birthDate));
			preparedStatement.execute();
			System.out.println("Dodano rekord");
		} catch (SQLException exception) {
			System.out.println("Blad prz wstawianiu danych");
			exception.printStackTrace();
			return false;
		}
		return true;
	}
	
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
