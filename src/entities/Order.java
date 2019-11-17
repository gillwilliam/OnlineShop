package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner")
	private User user;

	//bi-directional one-to-one association to ProductList
	@OneToOne
	@JoinColumn(name="items")
	private ProductList productList;

	public Order() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductList getProductList() {
		return this.productList;
	}

	public void setProductList(ProductList productList) {
		this.productList = productList;
	}

}