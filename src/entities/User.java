package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String email;

	private String firstName;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String lastName;

	private String password;

	private String phone;

	private String type;

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	// bi-directional many-to-one association to ProductList
	@OneToMany(mappedBy = "userBean")
	private List<ProductList> productLists;

	public User() {
	}

	public User(String firstName, String surname, String phone, String email, String password, String type,
			String address) {
		this(firstName, surname, phone, email, password, type);
		this.address = address;
	}

	public User(String firstName, String surname, String phone, String email, String password) {
		this(firstName, surname, phone, email, password, "BUYER");
	}

	public User(String firstName, String surname, String phone, String email, String password, String type) {
		this.firstName = firstName;
		this.lastName = surname;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.type = type == null ? "BUYER" : type;
		this.address = "";
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

	public List<ProductList> getProductLists() {
		return this.productLists;
	}

	public void setProductLists(List<ProductList> productLists) {
		this.productLists = productLists;
	}

	public ProductList addProductList(ProductList productList) {
		getProductLists().add(productList);
		productList.setUserBean(this);

		return productList;
	}

	public ProductList removeProductList(ProductList productList) {
		getProductLists().remove(productList);
		productList.setUserBean(null);

		return productList;
	}

	public boolean isBuyer() {
		return getType() != null && getType().equals("BUYER");
	}

	public boolean isSeller() {
		return getType() != null && getType().equals("SELLER");
	}

	public boolean isAdmin() {
		return getType() != null && getType().equals("ADMIN");
	}
}