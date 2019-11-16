package entities;

import java.io.Serializable;
import javax.persistence.*;

import beans.session.AdminBean;
import beans.session.BuyerBean;
import beans.session.SellerBean;
import beans.session.UserBean;

import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String phone;

	private String type;

	//bi-directional many-to-one association to ProductList
	@OneToMany(mappedBy="userBean")
	private List<ProductList> productLists;

	public User() {
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


	
	public void updateFromUserBean(UserBean userBean)
	{
		email 		= userBean.getEmail();
		firstName 	= userBean.getName();
		lastName 	= userBean.getSurname();
		password 	= userBean.getPassword();
		phone 		= userBean.getPhone();
		address		= "";	// will be changed to real address if user is instance of BuyerBean
		
		if (userBean instanceof BuyerBean)
		{
			address = ((BuyerBean) userBean).getAddress();
			type 	= "BUYER";
		}
		else if (userBean instanceof SellerBean)
			type = "SELLER";
		else if (userBean instanceof AdminBean)
			type = "ADMIN";
	}

}