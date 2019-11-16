package entities;

public class Seller extends User {

	// CONST ///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;

	public Seller() {
		super();
	}

	public Seller(String name, String surname, String phone, String email, String password) {
		super(name, surname, phone, email, password);
	}

}
