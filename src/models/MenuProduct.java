package models;

public class MenuProduct {
	private Product product;
	private int sugars;
	private int milks;
	private int creams;
	private int quantity;
	
	// For coffee products
	public MenuProduct(Product product, int sugars, int milks, int creams, int quantity) {
		this.product = product;
		this.setQuantity(quantity);
		
		if (this.product.isDrink()) {
			this.setSugars(sugars);
			this.setMilks(milks);
			this.setCreams(creams);
		}
	}
	
	// For menu products
	public MenuProduct(Product product, int quantity) {
		this.product = product;
		this.setQuantity(quantity);
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getSugars() {
		return sugars;
	}

	public void setSugars(int sugars) {
		this.sugars = sugars;
	}

	public int getMilks() {
		return milks;
	}

	public void setMilks(int milks) {
		this.milks = milks;
	}

	public int getCreams() {
		return creams;
	}

	public void setCreams(int creams) {
		this.creams = creams;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public String toString() {
		return this.quantity + "x " + this.product.getName() + " " + this.product.getPrice() + " $";
	}
}


