package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "category_id")
	private int categoryId;

	private String name;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "parent")
	private Category parent;

	// bi-directional many-to-one association to Category
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Category> categories;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "categoryBean", cascade = CascadeType.ALL, orphanRemoval = true)
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

	public Category addChild(Category category) {
		getChildren().add(category);
		category.setParent(this);

		return category;
	}

	public Category removeChild(Category category) {
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
		product.setCategory(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);

		return product;
	}

	public boolean isLeaf() {
		return getChildren().size() == 0;
	}

	public boolean isRoot() {
		return getParent() == null;
	}

	public String getSubcategoriesAsJavascriptArray() {
		StringBuilder res = new StringBuilder();
		res.append("[");
		for (Category subcategory : getChildren()) {
			res.append(subcategory.getCategoryId() + ",");
		}

		if (res.length() > 1)
			res = res.deleteCharAt(res.length() - 1);

		return res.toString() + "]";
	}

}