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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int categoryId;

	private String name;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="parent")
	private Category parent;

	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="parent")
	private List<Category> children;

	public Category() {
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

	public Category getCategory() {
		return this.parent;
	}

	public void setCategory(Category category) {
		this.parent = category;
	}

	public List<Category> getCategories() {
		return this.children;
	}

	public void setCategories(List<Category> categories) {
		this.children = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setCategory(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setCategory(null);

		return category;
	}

}