package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.*;

/**
 * The persistent class for the Buyer database table.
 * 
 */
@Entity
@NamedQuery(name = "Buyer.findAll", query = "SELECT b FROM Buyer b")
public class Buyer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String email;

	@Lob
	private String shoppingCart;

	@Lob
	private String wishList;

	// We load the products lazily
	// @ManyToMany(mappedBy="idProduct")
	//private List<Product> cachedshoppingCart;
	//private List<Product> cachedWishList;

	// bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name = "email")
	private User user;

	public Buyer() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShoppingCart() {
		return this.shoppingCart;
	}


	public void setShoppingCart(String shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public void setWishList(List<Product> wishList) {
		this.shoppingCart = getCSVFromProducts(wishList);
	}


	public void setWishList(String wishList) {
		this.wishList = wishList;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private String getCSVFromProducts(List<Product> list) {
		StringJoiner string = new StringJoiner(",");
		for (Product p : list) {
			string.add(Integer.toString(p.getIdProduct()));
		}
		return string.toString();
	}
}