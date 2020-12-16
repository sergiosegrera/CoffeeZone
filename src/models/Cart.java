package models;

public class Cart {
	private String id;
	private CartMenu[] menus;
	private double totalPrice;
	
	Cart(String id, CartMenu[] menus, double totalPrice) {
		this.setId(id);
		this.setMenus(menus);
		this.setTotalPrice(totalPrice);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CartMenu[] getMenus() {
		return menus;
	}

	public void setMenus(CartMenu[] menus) {
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
