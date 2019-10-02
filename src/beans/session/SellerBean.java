package beans.session;

public class SellerBean extends UserBean {

	// CONST ///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	
	
	
	public SellerBean()
    {
        super();
    }
    
    
    
    public SellerBean(String name, String surname, String phone, String email, String password)
    {
    	super(name, surname, phone, email, password);
    }

}
