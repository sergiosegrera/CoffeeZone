package client;

import database.Database;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Database db = new Database(
				"jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
				System.getenv("USERNAME"),
				System.getenv("PASSWORD")
				);
		Group root = new Group();
		
		Menu menu = new Menu(db, null);
		
		root.getChildren().add(menu);
		
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

}
