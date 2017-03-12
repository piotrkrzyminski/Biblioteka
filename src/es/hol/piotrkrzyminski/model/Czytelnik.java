package es.hol.piotrkrzyminski.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.hol.piotrkrzyminski.connection.Connect;

public class Czytelnik {
	private Connect connect = new Connect();

	private String firstName;
	private String secondName;
	private String surname;
	private int[] pesel = new int[11];
	private String peselString;
	private LocalDate birthDate;
	private String message;
	private boolean isDateProper = false;
	
	Random generator = new Random();
	
	public void addToDatabase() {
		if(isDateProper == true) {
			System.out.println(this.firstName);
			System.out.println(this.secondName);
			System.out.println(this.surname);
			System.out.println(this.peselString);
			System.out.println(this.birthDate);
			
			connect.insertCzytelnik(firstName, secondName, surname, peselString, birthDate);
			message = "Dodano nowy rekord do bazy danych";
		}
		else {
			message = "Wprowadzono niepoprawne dane";
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		if(firstName.matches("^[A-Z]{1}[a-z]{2,48}$")) {
			this.firstName = firstName;
			isDateProper = true;
		}
		else {
			isDateProper = false;
		}
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) {
		if(!secondName.equals("")) {
			Pattern pattern = Pattern.compile("^[A-¯]{1}[a-¿]{2,48}$");
			Matcher matcher = pattern.matcher(secondName);
			
			if(matcher.find()) {
				this.secondName = secondName;
				
				isDateProper = true;
			}
			else {
				isDateProper = false;
			}
		}
		else
			this.secondName = null;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		Pattern pattern = Pattern.compile("^[A-¯]{1}[a-¿]{2,48}$");
		Matcher matcher = pattern.matcher(surname);
		
		if(matcher.find()) {
			this.surname = surname;
			isDateProper = true;
		}
		else {
			isDateProper = false;
		}
	}
	
	public int[] getPesel() {
		return pesel;
	}
	
	public void setPesel(String peselText) {
		if(peselText.length()==11) {
			this.peselString = peselText;
			
			for(int i=0; i<peselText.length(); i++) {
				pesel[i] = Integer.parseInt(String.valueOf(peselText.charAt(i)));
			}
			System.out.println();
			isDateProper = true;
		}
		else {
			isDateProper = false;
		}
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			format.parse(birthDate.toString());
			this.birthDate = birthDate;
		} catch (ParseException exception) {
			isDateProper = false;
		}
		isDateProper = true;
	}
	
	public String getMessage() {
		return message;
	}
}
