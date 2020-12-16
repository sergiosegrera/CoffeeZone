package models;

public class Menu {
	private String id;
	private String name;
	private MenuProduct[] products;
	private double totalPrice;
	
	Menu(String id, String name, MenuProduct[] products, double totalPrice) {
		this.id = id;
		this.name = name;
		this.products = products;
		this.totalPrice = totalPrice;
	}
	
	// TODO: Overloaded constructor that omits the totalPrice and calculates it on its own

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuProduct[] getProducts() {
		return products;
	}

	public void setProducts(MenuProduct[] products) {
		this.products = products;
	}
	
	public void calculateTotalPrice() {
		// TODO: Calculate and add all of the prices of all the products + milks, creams, sugars
		// maybe change to return double? or just set totalPrice
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
