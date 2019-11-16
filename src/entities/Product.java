package entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import categories.Category;

@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product {

	// CONST
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int MAX_NAME_LEN = 100;
	public static final String NAME_REGEX = "^[a-zA-Z0-9  ]{1," + MAX_NAME_LEN + "}$";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Category category;

	@Lob
	private String description;

	private String image;

	private String name;

	private BigDecimal price;

	private int quantity;


	// bi-directional many-to-many association to ProductList
	@ManyToMany
	@JoinTable(name = "lists_to_products", joinColumns = { @JoinColumn(name = "productId") }, inverseJoinColumns = {
			@JoinColumn(name = "listId") })
	private List<ProductList> productLists;

	public Product() {
		name = description = image = "";
		price = new BigDecimal(0.0);
		category = null;
	}

	public Product(@NotNull String name, @NotNull Category category, @NotNull BigDecimal price,
			@NotNull String description, int quantity, @NotNull String imageStoragePath) {
		if (!name.matches(NAME_REGEX))
			throw new IllegalArgumentException("Name doesn't match required pattern");

		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.image = imageStoragePath + name;
	}

	public static boolean isNameValid(String name) {
		return name.matches(NAME_REGEX);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
}
