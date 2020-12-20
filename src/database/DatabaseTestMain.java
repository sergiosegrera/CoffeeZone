package database;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Customer;
import models.Product;

public class DatabaseTestMain {

	public static void main(String[] args) throws SQLException {
		Database db = new Database(
				"jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
				System.getenv("USERNAME"),
				System.getenv("PASSWORD")
				);
		
		// db.newCustomer("sergio", "hello123", "sergio@gmail.com", "+5149999999", "1606 Ottawa st", "");
		
		// System.out.println(db.login("sergio", "hello123"));
		
		Customer customer = db.getCustomer("sergio");
		System.out.println(customer.getUsername());
		System.out.println(customer.getCart().getId());
		System.out.println(customer.getCart().getTotalPrice());
		customer.getCart().setTotalPrice(20.5);
		db.saveCart(customer);
		
		ArrayList<Product> products = db.getProducts();
		
		for (Product product : products) {
			System.out.println(product.getName());
		}
		
		db.close();
	}

}
