package database;

import java.sql.SQLException;

public class DatabaseTestMain {

	public static void main(String[] args) throws SQLException {
		Database db = new Database(
				"jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
				System.getenv("USERNAME"),
				System.getenv("PASSWORD")
				);
		
		db.newCustomer("sergio", "hello123", "sergio@gmail.com", "+5149999999", "1606 Ottawa st", "");
		
		System.out.println(db.login("sergio", "hello123"));
		
		System.out.println(db.getCustomer("sergio"));
		
		db.close();
	}

}
