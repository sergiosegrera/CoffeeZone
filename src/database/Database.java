package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import models.Cart;
import models.CartMenu;
import models.Customer;
import models.Menu;
import models.MenuProduct;
import models.Order;
import models.Product;
import utils.Hasher;

// Change name to database controller?
public class Database {
	private Connection con;
	private Hasher hasher;
	
	public Database(String address, String username, String password) {
		// jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca
		this.con = createConnection(address, username, password);
		this.hasher = new Hasher();
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
		
	public Customer newCustomer(String name, String password, String email, String phone, String address, String refferedBy) throws SQLException {
		PreparedStatement stmt = null;		
		
		String salt = this.hasher.getSalt();
		byte[] hashAndSaltedPassword = this.hasher.hash(password, salt);
		
		int cartId = 0;
		
		try {
			String string = "INSERT INTO customers VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(string);
						
			stmt.setString(1, name);
			stmt.setBytes(2, hashAndSaltedPassword);
			stmt.setString(3, salt);
			stmt.setString(4, address);			
			stmt.setString(5, phone);		
			stmt.setString(6, email);			
			stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			stmt.setString(8, refferedBy);	
			
			stmt.execute();
			con.commit();
			
			PreparedStatement insertCartStmt = con.prepareCall("INSERT INTO carts VALUES (DEFAULT, ?, ?)");
			insertCartStmt.setString(1, name);
			insertCartStmt.setInt(2, 0);
			
			insertCartStmt.execute();
			con.commit();
			
			PreparedStatement cartIdStmt = con.prepareCall("SELECT cart_id FROM carts WHERE customer_id = ?");
			cartIdStmt.setString(1, name);
			
			ResultSet result = cartIdStmt.executeQuery();
			con.commit();
			
			while (result.next()) {
				cartId = result.getInt("cart_id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			stmt.close();
		}
		
		return new Customer(name, phone, email, address, refferedBy, new Cart(cartId));
	}
	
	private Product getProduct(int id) throws SQLException {
		Product product = null;
		
		String string = "SELECT name, is_drink, stock, price FROM products WHERE product_id = ?";
		CallableStatement stmt = con.prepareCall(string);
		
		stmt.setInt(1, id);
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			boolean isDrink = false;
			if (result.getInt("is_drink") > 0) {
				isDrink = true;
			}
			
			product = new Product(
					id,
					result.getString("name"),
					isDrink,
					result.getInt("stock"),
					result.getDouble("price")
					);
		}
		
		stmt.close();
		return product;
	}
	
	private ArrayList<MenuProduct> getMenuProducts(int id) throws SQLException {
		ArrayList<MenuProduct> menuProducts = new ArrayList<MenuProduct>();
		
		String string = "SELECT product_id, sugars, milks, creams, quantity FROM menu_products WHERE menu_id = ?";
		CallableStatement stmt = con.prepareCall(string);
		
		stmt.setInt(1, id);
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			menuProducts.add(new MenuProduct(
					getProduct(result.getInt("product_id")),
					result.getInt("sugars"),
					result.getInt("milks"),
					result.getInt("creams"),
					result.getInt("quantity")
					));
		}
		
		stmt.close();
		return menuProducts;
		
	}
	
	private Menu getMenu(int id) throws SQLException {
		Menu menu = null;
		
		String string = "SELECT name, total_price FROM menus WHERE menu_id = ?";
		CallableStatement stmt = con.prepareCall(string);
		
		stmt.setInt(1, id);
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			return new Menu(
					id,
					result.getString("name"),
					getMenuProducts(id),
					result.getDouble("total_price")
					);
		}
		
		stmt.close();
		return menu;
	}
	
	private ArrayList<CartMenu> getCartMenus(int id) throws SQLException {
		ArrayList<CartMenu> cartMenus = new ArrayList<CartMenu>();
		
		String string = "SELECT menu_id, quantity FROM cart_menus WHERE cart_id = ?";
		CallableStatement stmt = con.prepareCall(string);
		
		stmt.setInt(1, id);
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			cartMenus.add(new CartMenu(getMenu(result.getInt("menu_id")), result.getInt("quantity")));
		}
		
		stmt.close();
		return cartMenus;	
	}
	
	private Cart getCart(String username) throws SQLException {
		Cart cart = null;
		
		String string = "SELECT cart_id, total_price FROM carts WHERE customer_id = ?";
		CallableStatement stmt = con.prepareCall(string);
		stmt.setString(1, username);
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			int id = result.getInt("cart_id");
			double totalPrice = result.getDouble("total_price");
			
			cart = new Cart(id, getCartMenus(id), totalPrice);
		}
		
		stmt.close();
		
		return cart;	
	}
	
	public Customer getCustomer(String username) throws SQLException {
		CallableStatement stmt = null;
		Customer customer = null;
		
		// Preparation
		String string = "SELECT address, phone, email, reffered_by FROM customers WHERE customer_id = ?";
		stmt = con.prepareCall(string);
		stmt.setString(1, username);
		
		// Execution
		ResultSet result = stmt.executeQuery();
		con.commit();
		while (result.next()) {
			// Collection
			String address = result.getString("address");
			String phone = result.getString("phone");
			String email = result.getString("email");
			String refferedBy = result.getString("reffered_by");
			
			customer = new Customer(username, phone, email, address, refferedBy, getCart(username));
		}
		
		stmt.close();
		return customer;
	}
	
	public boolean login(String username, String password) throws SQLException {
		PreparedStatement stmt = null;
		
		String salt = null;
		byte[] hash = null;
		
		try {
			String string = "SELECT salt, password FROM customers WHERE customer_id = ?";
			stmt = this.con.prepareCall(string);
			
			stmt.setString(1, username);
			
			ResultSet result = stmt.executeQuery();
			con.commit();
			
			while (result.next()) {
				salt = result.getString(1);
				hash = result.getBytes(2);
								
				byte[] hashedAndSaltedPassword = this.hasher.hash(password, salt);
				
				if (Arrays.equals(hash, hashedAndSaltedPassword)) {
					return true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return false;
	}
	
	public ArrayList<Product> getProducts() throws SQLException {
		ArrayList<Product> products = new ArrayList<Product>();
		
		String string = "SELECT * FROM products";
		PreparedStatement stmt = con.prepareCall(string);	
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			boolean isDrink = false;
			if (result.getInt("is_drink") > 0) {
				isDrink = true;
			}
			
			products.add(new Product(
					result.getInt("product_id"),
					result.getString("name"),
					isDrink,
					result.getInt("stock"),
					result.getDouble("price")
					));
		}
		
		stmt.close();	
		return products;
	}
	
	public ArrayList<Menu> getMenus() throws SQLException {
		ArrayList<Menu> menus = new ArrayList<Menu>();
				
		String string = "SELECT * FROM menus";
		PreparedStatement stmt = con.prepareCall(string);	
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			int id = result.getInt("menu_id");
			menus.add(new Menu(
					id,
					result.getString("name"),
					getMenuProducts(id),
					result.getDouble("total_price")
					));
		}
		
		stmt.close();
		return menus;
	}
	
	public void saveCart(Customer customer) throws SQLException {
		// Check if cart with the same cart_id && customer_id exists
		// If yes delete cart_menus with cart_id
		// and insert new cart
		String string = "SELECT * FROM carts WHERE cart_id = ? AND customer_id = ?";
		PreparedStatement stmt = con.prepareCall(string);
		
		
		stmt.setInt(1, customer.getCart().getId());		
		stmt.setString(2, customer.getUsername());
		
		ResultSet result = stmt.executeQuery();
		con.commit();
		
		while (result.next()) {
			PreparedStatement deleteStmt = con.prepareCall("DELETE FROM cart_menus WHERE menu_id = ?");
			deleteStmt.setInt(1, customer.getCart().getId());
			
			deleteStmt.execute();
			con.commit();	
			deleteStmt.close();
			
			PreparedStatement updateCartStmt = con.prepareStatement("UPDATE carts SET total_price = ? WHERE cart_id = ?");
			updateCartStmt.setDouble(1, customer.getCart().getTotalPrice());
			updateCartStmt.setInt(2, customer.getCart().getId());
			
			updateCartStmt.execute();
			con.commit();
			updateCartStmt.close();
		}
		
		for (CartMenu cartMenu : customer.getCart().getMenus()) {	
			PreparedStatement insertStmt = con.prepareCall("INSERT INTO cart_menus VALUES (?, ?, ?)");
			stmt.setInt(1, customer.getCart().getId());
			stmt.setInt(2, cartMenu.getMenu().getId());
			stmt.setInt(3, cartMenu.getQuantity());
			
			insertStmt.execute();
			con.commit();
			insertStmt.close();
		}
		
		stmt.close();
	}
	
	private void saveMenuProducts(Menu menu) throws SQLException {
		for (MenuProduct menuProduct : menu.getProducts()) {
			PreparedStatement stmt = con.prepareCall("INSERT INTO menu_products VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, menu.getId());
			stmt.setInt(2, menuProduct.getProduct().getId());
			stmt.setInt(3, menuProduct.getSugars());			
			stmt.setInt(4, menuProduct.getMilks());
			stmt.setInt(5, menuProduct.getCreams());
			stmt.setInt(6, menuProduct.getQuantity());
			
			stmt.execute();
			this.con.commit();
			
			stmt.close();
		}
	}
	
	public void saveMenu(Menu menu) throws SQLException {
		if (menu.getId() == 0) {
			// new menu
			// insert menu + menu_products
			PreparedStatement insertMenuStmt = con.prepareStatement("INSERT INTO menus VALUES(DEFAULT, ?, ?)");
			
			
			insertMenuStmt.setString(1, menu.getName());
			insertMenuStmt.setDouble(2, menu.getTotalPrice());
			
			insertMenuStmt.executeUpdate();
			con.commit();	
			insertMenuStmt.close();
			
			PreparedStatement menuIdStmt = con.prepareStatement("SELECT menu_id FROM menus WHERE name = ?");	
			menuIdStmt.setString(1, menu.getName());
			
			ResultSet result = menuIdStmt.executeQuery();
			con.commit();
			
			while (result.next()) {
				menu.setId(result.getInt("menu_id"));
			}	
				
			saveMenuProducts(menu);
			
		} else {
			// old menu			
			// update total_price
			PreparedStatement updateMenuStmt = con.prepareStatement("UPDATE menus SET name = ?, total_price = ? WHERE menu_id = ?");
			
			updateMenuStmt.setString(1, menu.getName());
			updateMenuStmt.setDouble(2, menu.getTotalPrice());
			updateMenuStmt.setInt(3, menu.getId());
			
			updateMenuStmt.execute();
			con.commit();
			updateMenuStmt.close();
			
			// delete menu_products and add them		
			PreparedStatement deleteMenuProductsStmt = con.prepareStatement("DELETE menu_products WHERE menu_id = ?");
			
			deleteMenuProductsStmt.setInt(1, menu.getId());
			
			deleteMenuProductsStmt.execute();
			con.commit();
			deleteMenuProductsStmt.close();
			
			saveMenuProducts(menu);
		}
	}
	
	public void createOrder(Order order) throws SQLException {
		PreparedStatement insertStmt = con.prepareStatement("INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?, ?, sysdate, null, null)");
		insertStmt.setString(1, order.getCustomer().getUsername());
		insertStmt.setInt(2, order.getCart().getId());
		insertStmt.setString(3, order.getCard());
		insertStmt.setString(4, order.getCvc());
		insertStmt.setString(5, order.getAddress());
		
		insertStmt.execute();
		con.commit();
		insertStmt.close();
		
		PreparedStatement updateCartCustomerIdStmt = con.prepareStatement("UPDATE carts SET customer_id = 0 WHERE cart_id = ?");
		updateCartCustomerIdStmt.setInt(1, order.getCart().getId());
		
		updateCartCustomerIdStmt.execute();
		con.commit();
		updateCartCustomerIdStmt.close();
	}
		
	public void rollback() throws SQLException {
		this.con.rollback();
	}
	
	public void close() throws SQLException {
		this.con.close();
	}
}
