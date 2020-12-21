package client;

import database.Database;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Customer;

public class Client extends Application {
	private Database db;
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		db = new Database(
				"jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
				System.getenv("USERNAME"),
				System.getenv("PASSWORD")
				);
		Group root = new Group();
		
		// Menu menu = new Menu(db, null);
		Login loginPane = new Login(this, db);
		
		root.getChildren().add(loginPane);
		
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	public void viewMenu(Customer customer) {
		Group root = new Group();
		
		Menu menu = new Menu(this, db, customer);
		
		root.getChildren().add(menu);
		
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	public void viewCheckout(Customer customer) {
		Group root = new Group();
		
		Checkout checkoutPane = new Checkout(this, db, customer);
		
		root.getChildren().add(checkoutPane);
		
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

}
