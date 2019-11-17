package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_id")
	private int categoryId;

	private String name;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="parent")
	private Category parent;

	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="parent")
	private List<Category> categories;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="categoryBean")
	private List<Product> products;

	public Category() {
		name = "";
	}

	public Category(String name, Category parent) {
		this.parent = parent;
		this.name = name;
	}
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return this.parent;
	}

	public void setParent(Category category) {
		this.parent = category;
	}

	public List<Category> getChildren() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getChildren().add(category);
		category.setParent(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getChildren().remove(category);
		category.setParent(null);

		return category;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategoryBean(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategoryBean(null);

		return product;
	}

}