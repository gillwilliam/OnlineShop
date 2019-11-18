package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import utils.Price;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String description;

	private String image;

	private String name;

	private BigDecimal price;

	private int quantity;

	//bi-directional many-to-many association to ProductList
	@ManyToMany
	@JoinTable(
		name="lists_to_products"
		, joinColumns={
			@JoinColumn(name="productId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="listId")
			}
		)
	private List<ProductList> productLists;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category")
	private Category categoryBean;

	public Product() {
	}

	public Product(String name, Category cat, Price price, String desc, int quantity, String image) {
		this.setName(name);
		this.setCategory(cat);
		this.setDescription(desc);
		this.setImage(image);
		this.setPrice(price);
		this.setQuantity(quantity);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<ProductList> getProductLists() {
		return this.productLists;
	}

	public void setProductLists(List<ProductList> productLists) {
		this.productLists = productLists;
	}

	public Category getCategory() {
		return this.categoryBean;
	}

	public void setCategory(Category categoryBean) {
		this.categoryBean = categoryBean;
	}

}