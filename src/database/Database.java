package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import models.Cart;
import models.Customer;
import models.Menu;
import models.Product;

// Change name to database controller?
public class Database {
	private Connection con;
	
	Database(String address, String username, String password) {
		// jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca
		this.con = createConnection(address, username, password);
	}
	
	private Connection createConnection(String address, String username, String password) {
		Connection con = null;
		Properties ConnProps = new Properties();
		ConnProps.put("user", username);
		ConnProps.put("password", password);
		try {
		      con = DriverManager.getConnection(address, ConnProps);
			  con.setAutoCommit(false);
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}	
	
	public Customer newCustomer(String name, String password, String email, String phone, String address) {
		// Create customer + create cart
		return null;
	}
	
	public Customer login(String username, String password) {
		return null;
	}
	
	public Product[] getProducts() {
		
		return null;
	}
	
	public Menu[] getMenus() {
		
		return null;
	}
	
	public void saveCart(Cart cart) {
		
	}
	
	public void saveMenu(Menu menu) {
		
	}
	
	//public void createOrder()
	
	// Save Order (Insert)
	
	// close() to close connection
	
	public void close() throws SQLException {
		this.con.close();
	}
}
