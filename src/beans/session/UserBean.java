package beans.session;

import java.io.Serializable;

public class UserBean implements Serializable {

    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final long serialVersionUID = 1;

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String      name;
    private String      surname;
    private String      phone;
    private String      address;
    private String      email;
    private String      password;
    private UserType    userType;



    public UserBean()
    {
        name = surname = phone = address = email = password = "";
        userType = UserType.UNREGISTERED;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
