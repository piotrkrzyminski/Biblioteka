/**
 * Klasa definiuje z jakich danych bêdzie siê sk³adaæ obiekt czytelnika, który zostanie dodany do bazy danch
 * Wymagane dane to: imiê, nazwisko, pesel i data urodzenia. drugie imiê jest opcjonalne
 * Klasa sprawdza ponadto dane pod k¹tem niepoprawnych wartoœci
 */

package es.hol.piotrkrzyminski.model;

import java.time.LocalDate;

public class Czytelnik {
	private String firstName; //imiê
	private String secondName; //drugie imiê
	private String surname; //nazwisko
	private String pesel; //pesel
	private LocalDate birthDate; //data urodzenia
	private String message; //treœæ informacji zwracana do intefejsu i informuj¹ca o poprawnoœci lub niepoprawnoœci wprowadzonych danych
	
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
			message = "Wyst¹pi³ b³¹d. Nie dodano nowego rekordu do bazy danych";
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