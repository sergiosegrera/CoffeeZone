package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.CartMenu;
import models.Customer;
import models.Order;

public class Checkout extends Pane {
	private Client client;
	private Database db;
	private Customer customer;
	
	@FXML
	private TextField quantityField;
	@FXML
	private TextField cardField;
	@FXML
	private TextField cvcField;
	@FXML
	private TextField addressField;
	@FXML
	private ListView<CartMenu> menuList;
	@FXML
	private Text totalText;
	
	public Checkout(Client client, Database db, Customer customer) {
		this.client = client;
		this.db = db;
		this.customer = customer;
		
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
    		loader.setController(this);
    		loader.setRoot(this);
    		loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}	
		menuList.getItems().addAll(customer.getCart().getMenus());
		
		addressField.setText(customer.getAddress());
		
		this.customer.getCart().calculateTotalPrice();
		updateTotal();
	}
	
	private void updateTotal() {
		totalText.setText("Total: " + customer.getCart().getTotalPrice());
	}
	
	@FXML
	private void backToProductAction(ActionEvent event) {
		this.client.viewMenu(customer);
	}
	
	private int stringToInt(String string) {
		int value;
		try {
			value = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			value = 0;
		}
		return value;
	}
	
	@FXML
	private void submitQuantityAction(ActionEvent event) {
		CartMenu selectedMenu = menuList.getSelectionModel().getSelectedItem();
		if (selectedMenu != null) {
			int quantity = stringToInt(quantityField.getText());
			ArrayList<CartMenu> menus = customer.getCart().getMenus();
			
			for (CartMenu menu : menus) {
				if (menu.getMenu().getId() == selectedMenu.getMenu().getId() && menu.getMenu().getName() == selectedMenu.getMenu().getName()) {
					menu.setQuantity(quantity);
				}
			}
			
			customer.getCart().setMenus(menus);	
	
			this.customer.getCart().calculateTotalPrice();
			updateTotal();
		}
	}
	
	@FXML
	private void checkoutAction(ActionEvent event) {	
		try {
			db.saveCart(customer);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		if (!cardField.getText().equals("") && cvcField.getText().equals("")) {	
			Order order = new Order(customer, customer.getCart(), cardField.getText(), cvcField.getText(), addressField.getText());
			
			try {	
				db.createOrder(order);
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			} finally {
				menuList.getItems().clear();
				customer.getCart().setMenus(new ArrayList<CartMenu>());
			}
		}
		
	}
}
