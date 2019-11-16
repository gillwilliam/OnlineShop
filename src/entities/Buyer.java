package entities;

public class Buyer extends User {

	// CONST /////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;

	// fields ////////////////////////////////////////////////////////////////////
	private String address;

	public Buyer() {
		super();

		address = "";
	}

	public Buyer(String name, String surname, String phone, String address, String email, String password) {
		super(name, surname, phone, email, password);

		this.address = address;
	}

	// getters && setters ////////////////////////////////////////////////////////

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

}
