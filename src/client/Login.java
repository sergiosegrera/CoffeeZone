package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.Customer;
import models.Product;

public class Login extends Pane {
	private Client client;
	private Database db;
	private ArrayList<Product> products;
	
	@FXML
	private ListView<Product> productList;
	@FXML
	private TextField loginUsernameField;
	@FXML
	private PasswordField loginPasswordField;
	
	@FXML
	private TextField registerUsernameField;
	@FXML
	private TextField registerEmailField;
	@FXML
	private TextField registerPhoneField;
	@FXML
	private TextField registerAddressField;	
	@FXML
	private PasswordField registerPasswordField;
	@FXML
	private TextField registerRefferedByField;
	
	public Login(Client client, Database db) {
		this.client = client;
    	this.db = db;
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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
	
	@FXML
	private void loginAction(ActionEvent event) {
		boolean result = false;
		try {
			result = db.login(this.loginUsernameField.getText(), this.loginPasswordField.getText());
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		if (result == true) {
			Customer customer = null;
			try {
				customer = db.getCustomer(this.loginUsernameField.getText());
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			client.viewMenu(customer);
		}
	}
	
	@FXML
	private void registerAction(ActionEvent event) {
		String username = this.registerUsernameField.getText();
		String email = this.registerEmailField.getText();
		String phone = this.registerPhoneField.getText();
		String address = this.registerAddressField.getText();
		String password = this.registerPasswordField.getText();
		
		if (!username.equals("") && !email.equals("") && !phone.equals("") && !address.equals("") && !password.equals("")) {
			try {
				db.newCustomer(username, password, email, phone, address, this.registerRefferedByField.getText());
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					db.rollback();
					return;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			Customer customer = null;
			
			try {
				customer = db.getCustomer(username);
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
			client.viewMenu(customer);
			
		}
	}
	
}
