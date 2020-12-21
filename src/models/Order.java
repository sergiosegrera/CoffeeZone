package models;

public class Order {
	private Customer customer;
	private Cart cart;
	private String card;
	private String cvc;
	private String address;
	
	public Order(Customer customer, Cart cart, String card, String cvc, String address) {
		this.customer = customer;
		this.cart = cart;
		this.card = card;
		this.cvc = cvc;
		this.address = address;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
