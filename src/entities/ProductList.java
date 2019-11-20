package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the product_lists database table.
 * 
 */
@Entity
@Table(name = "product_lists")
@NamedQuery(name = "ProductList.findAll", query = "SELECT p FROM ProductList p")
public class ProductList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int listId;

	private boolean isShoppingCart;

	//bi-directional many-to-many association to Product
	@ManyToMany(mappedBy="productLists", cascade = CascadeType.ALL)
	@JoinTable(name = "lists_to_products", joinColumns = { @JoinColumn(name = "productId") }, inverseJoinColumns = {
			@JoinColumn(name = "listId") })
	private List<Product> products;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user")
	private User userBean;

	// bi-directional one-to-one association to Order
	@OneToOne(mappedBy = "productList")
	private Order order;
	
	public ProductList(List<Product> products, User userBean) {
		this.products = products;
		this.userBean = userBean;
		this.isShoppingCart = false;
	}

	public ProductList() {
	}

	public int getListId() {
		return this.listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public boolean getIsShoppingCart() {
		return this.isShoppingCart;
	}

	public void setIsShoppingCart(boolean isShoppingCart) {
		this.isShoppingCart = isShoppingCart;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}