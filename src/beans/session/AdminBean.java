package beans.session;

public class AdminBean extends UserBean {

	// CONST ///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	
	
	
	public AdminBean()
    {
        super();
    }
    
    
    
    public AdminBean(String name, String surname, String phone, String email, String password)
    {
    	super(name, surname, phone, email, password);
    }

}
