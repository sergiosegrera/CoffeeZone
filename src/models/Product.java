package models;

public class Product {
	private String id;
	private String name;
	private boolean isDrink;
	private int stock;
	private double price;
	
	Product(String id, String name, boolean isDrink, int stock, double price) {
		this.setId(id);
		this.setName(name);
		this.setDrink(isDrink);
		this.setStock(stock);
		this.setPrice(price);
	}

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
	
	public boolean isDrink() {
		return isDrink;
	}

	public void setDrink(boolean isDrink) {
		this.isDrink = isDrink;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
