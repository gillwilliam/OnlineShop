package entities;

public class Admin extends User {

	// CONST ///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}

	public Admin(String name, String surname, String phone, String email, String password) {
		super(name, surname, phone, email, password);
	}

}
