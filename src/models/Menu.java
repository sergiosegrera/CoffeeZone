package models;

import java.util.ArrayList;

public class Menu {
	private int id;
	private String name;
	private ArrayList<MenuProduct> products;
	private double totalPrice;
	
	public Menu(int id, String name, ArrayList<MenuProduct> products, double totalPrice) {
		this.id = id;
		this.name = name;
		this.products = products;
		this.totalPrice = totalPrice;
	}
	
	public Menu(String name) {
		this.name = name;
		this.products = new ArrayList<MenuProduct>();
	}
	
	// TODO: Overloaded constructor that omits the totalPrice and calculates it on its own

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<MenuProduct> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<MenuProduct> products) {
		this.products = products;
	}
	
	public void calculateTotalPrice() {
		double total = 0;
		
		for (MenuProduct menuProduct : products) {
			total += menuProduct.getQuantity() * menuProduct.getProduct().getPrice();
			// TODO: Add cream and stuff price
		}
		
		this.totalPrice = total;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}	
}
