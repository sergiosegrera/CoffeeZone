package models;

import java.util.ArrayList;

public class Cart {
	private int id;
	private ArrayList<CartMenu> menus;
	private double totalPrice;
	
	public Cart() {
		this.setTotalPrice(0);
	}
	
	public Cart(int id, ArrayList<CartMenu> menus, double totalPrice) {
		this.setId(id);
		this.setMenus(menus);
		this.setTotalPrice(totalPrice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<CartMenu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<CartMenu> menus) {
		this.menus = menus;
	}
	
	public void calculateTotalPrice() {
		// TODO: calculate all the menu prices
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
