package beans.session;

import entities.User;

public class BuyerBean extends UserBean {

	// CONST /////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	
	// fields ////////////////////////////////////////////////////////////////////
	private String      address;
	
	
	
	public BuyerBean()
	{
		super();
		
		address = "";
	}
	
	
	
	public BuyerBean(String name, String surname, String phone, String address, String email, String password)
    {
    	super(name, surname, phone, email, password);
    	
    	this.address = address;
    }
	
	
	
	@Override
	public void initWithEntity(User userEntity)
	{
		super.initWithEntity(userEntity);
		
		address = userEntity.getAddress();
	}
	
	
	
	// getters && setters ////////////////////////////////////////////////////////
	
	
	


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
