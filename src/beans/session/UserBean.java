package beans.session;

import java.io.Serializable;

public abstract class UserBean implements Serializable {

    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final long serialVersionUID = 1;

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String      name;
    private String      surname;
    private String      phone;
    private String      email;
    private String      password;



    public UserBean()
    {
        name = surname = phone = email = password = "";
    }
    
    
    
    public UserBean(String name, String surname, String phone, String email, String password)
    {
    	this.name 		= name;
    	this.surname 	= surname;
    	this.phone 		= phone;
    	this.email 		= email;
    	this.password 	= password;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
