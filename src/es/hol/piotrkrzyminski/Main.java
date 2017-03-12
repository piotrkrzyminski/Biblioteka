package es.hol.piotrkrzyminski;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	private BorderPane pane;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("Biblioteka");
		
		loadFXML();
		drawScene();
	}
	
	private void loadFXML() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/MainFXML.fxml"));
			pane = (BorderPane) loader.load();
			System.out.println("Wczytano plik FXML.");
		} catch (IOException exception) {
			System.out.println("Nie uda³o siê wczytaæ pliku FXML.");
			exception.printStackTrace();
		}
	}
	
	private void drawScene() {
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}
