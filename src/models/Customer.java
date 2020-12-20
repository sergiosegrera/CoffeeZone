package models;

public class Customer {
	private String username;
	private String phone;
	private String email;
	private String address;
	private String refferedBy;
	private Cart cart;
	
	public Customer(String username, String phone, String email, String address, String refferedBy, Cart cart) {
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.cart = cart;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getRefferedBy() {
		return refferedBy;
	}

	public void setRefferedBy(String refferedBy) {
		this.refferedBy = refferedBy;
	}
}
