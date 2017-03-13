/**
 * Klasa definiuje z jakich danych b�dzie si� sk�ada� obiekt czytelnika, kt�ry zostanie dodany do bazy danch
 * Wymagane dane to: imi�, nazwisko, pesel i data urodzenia. drugie imi� jest opcjonalne
 * Klasa sprawdza ponadto dane pod k�tem niepoprawnych warto�ci
 */

package es.hol.piotrkrzyminski.model;

import java.time.LocalDate;

public class Czytelnik {
	private String firstName; //imi�
	private String secondName; //drugie imi�
	private String surname; //nazwisko
	private String pesel; //pesel
	private LocalDate birthDate; //data urodzenia
	private String message; //tre�� informacji zwracana do intefejsu i informuj�ca o poprawno�ci lub niepoprawno�ci wprowadzonych danych
	
	/**
	 * konstruktor
	 * @param firstName
	 * @param secondName
	 * @param surname
	 * @param pesel
	 * @param birthDate
	 */
	public Czytelnik(String firstName, String secondName, String surname, String pesel, LocalDate birthDate) {
		if(firstName!=null && surname!=null && pesel!=null && birthDate!=null) {
			this.firstName = firstName;
			this.secondName = secondName;
			this.surname = surname;
			this.pesel = pesel;
			this.birthDate = birthDate;
			message = "Dodano do bazy danych nowy rekord";
		}
		else 
			message = "Wyst�pi� b��d. Nie dodano nowego rekordu do bazy danych";
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}

	public String getSurname() {
		return surname;
	}
	
	public String getPesel() {
		return pesel;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public String getMessage() {
		return message;
	}
}