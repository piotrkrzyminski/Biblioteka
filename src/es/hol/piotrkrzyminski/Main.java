package es.hol.piotrkrzyminski;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage; //okno aplkacji
	private BorderPane pane; //panel zawieraj�cy wszystkie elementy aplikacji
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("Biblioteka"); //ustaw tytu� aplikacji
		
		loadFXML();
		drawScene();
	}
	
	/**
	 * wczytaj plik FXML definiuj�cy wygl�d aplikacji
	 */
	private void loadFXML() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/MainFXML.fxml"));
			pane = (BorderPane) loader.load();
			System.out.println("Wczytano plik FXML.");
		} catch (IOException exception) {
			System.out.println("Nie uda�o si� wczyta� pliku FXML.");
			exception.printStackTrace();
		}
	}
	
	/**
	 * tworzy now� scen�, przypisuje j� do okna i pokazuje je
	 */
	private void drawScene() {
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}
