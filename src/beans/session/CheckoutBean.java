package beans.session;

import java.io.Serializable;

import javax.ejb.Stateful;

@Stateful
public class CheckoutBean implements Serializable {

    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final long serialVersionUID = 1;

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String name;

    public CheckoutBean()
    {
        name = "";
    }
    
    
    
    public CheckoutBean(String name)
    {
    	this.name = name;
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

}
