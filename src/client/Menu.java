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
import javafx.scene.text.Text;
import models.CartMenu;
import models.Customer;
import models.MenuProduct;
import models.Product;

public class Menu extends Pane {
	private Database db;
	private Customer customer;
	private models.Menu menu;
	private ArrayList<Product> products;
	
	@FXML
	private TextField menuTextField;
	@FXML
	private TextField quantityField;
	@FXML
	private ListView<Product> productList;
	@FXML
	private ListView<MenuProduct> menuProductList;
	@FXML
	private TextField milkField;
	@FXML
	private TextField creamsField;
	@FXML
	private TextField sugarsField;
	@FXML
	private Text menuTotal;
	@FXML
	private TextField menuNameField;
    
    public Menu(Database db, Customer customer) {
    	this.db = db;
    	this.customer = customer;
    	this.menu = new models.Menu(null);
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
    private void addToMenu(ActionEvent event) {
    	Product product = productList.getSelectionModel().getSelectedItem();    	
    	if (product != null) {
			ArrayList<MenuProduct> products = this.menu.getProducts();		
			int quantity = stringToInt(quantityField.getText());
			
			if (product.isDrink()) {
				int milks = stringToInt(milkField.getText());
				int creams = stringToInt(creamsField.getText());
				int sugars = stringToInt(sugarsField.getText());
				
				products.add(new MenuProduct(product, sugars, milks, creams, quantity));
				
			} else {
				products.add(new MenuProduct(product, quantity));
			}
			this.menu.setProducts(products);
			this.menu.calculateTotalPrice();
			menuTotal.setText("Total Menu Price: " + this.menu.getTotalPrice() + "$");
			menuProductList.getItems().clear();
			menuProductList.getItems().addAll(products);
    	} 
    }
    
    @FXML
    private void addMenuToCart(ActionEvent event) {
    	String menuTitle = menuNameField.getText();
    	if (menuTitle.equals("")) {
    		menuTitle = customer.getUsername() + "'s Menu";
    	}
    	if (menu.getProducts().size() > 0) {
    		ArrayList<CartMenu> menus = customer.getCart().getMenus();
    		menus.add(new CartMenu(this.menu, 1));
    		customer.getCart().setMenus(menus);
    		try {
				db.saveMenu(menu);
			} catch (SQLException e) {
				
				// ERROR CANT ADD to db;
				try {
					db.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
    		
    		this.menu = new models.Menu("");    		
			menuProductList.getItems().clear();
			menuNameField.setText("");
    	}
    }

    
}