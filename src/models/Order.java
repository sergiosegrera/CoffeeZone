package models;

public class Order {
	private int id;
	private Customer customer;
	private Cart cart;
	private String card;
	private String cvc;
	private String address;
	
	public Order(int id, Customer customer, Cart cart, String card, String cvc, String address) {
		this.id = id;
		this.customer = customer;
		this.cart = cart;
		this.card = card;
		this.cvc = cvc;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
