package models;

public class CartMenu {
	private Menu menu;
	private int quantity;
	
	CartMenu(Menu menu, int quantity) {
		this.menu = menu;
		this.setQuantity(quantity);
	}

	public Menu getMenu() {
		return menu;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
