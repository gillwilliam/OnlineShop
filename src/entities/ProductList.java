package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int listId;

	private byte isShoppingCart;

	// bi-directional many-to-many association to Product
	@ManyToMany(mappedBy = "productLists")
	private List<Product> products;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user")
	private User userBean;

	public ProductList() {
	}

	public int getListId() {
		return this.listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public byte getIsShoppingCart() {
		return this.isShoppingCart;
	}

	public void setIsShoppingCart(byte isShoppingCart) {
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

}