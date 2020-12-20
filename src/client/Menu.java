package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.Customer;
import models.Product;

public class Menu extends Pane {
	private Database db;
	private Customer customer;
	private ArrayList<Product> products;
	
	@FXML
	private TextField menuTextField;
	@FXML
	private TextField quantityField;
	@FXML
	private ListView<Product> productList;
	@FXML
	private TextField milkField;
	@FXML
	private TextField creamsField;
	@FXML
	private TextField sugarsField;
	
    @FXML
    private void addToMenu(ActionEvent event) {
    	Product product = productList.getSelectionModel().getSelectedItem();
    	if (product.isDrink()) {
    		int milks = Integer.parseInt(milkField.getText());
    		int creams = Integer.parseInt(creamsField.getText());
    		int sugars = Integer.parseInt(sugarsField.getText());
    	}
    	System.out.print(product.getName());
    }
    
    public Menu(Database db, Customer customer) {
    	this.db = db;
    	this.customer = customer;
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
    		loader.setController(this);
    		loader.setRoot(this);
    		loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	try {
			products = db.getProducts();	
			productList.getItems().addAll(products);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}