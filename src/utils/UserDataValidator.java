package utils;

import com.sun.istack.internal.NotNull;

public class UserDataValidator {

    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int MIN_NAME_LEN 		= 2;
    private static final int MAX_NAME_LEN 		= 60;
    private static final int MIN_SURNAME_LEN 	= 2;
    private static final int MAX_SURNAME_LEN 	= 60;
    private static final int MIN_PHONE_LEN		= 5;
    private static final int MAX_PHONE_LEN	    = 15;
    private static final int MIN_ADDR_LEN		= 10;
    private static final int MAX_ADDR_LEN		= 200;
    private static final int MIN_PASS_LEN		= 6;
    private static final int MAX_PASS_LEN		= 50;



    /**
     * Checks whether provided name is valid.
     * @param name - must be trimmed and have redundant spaces removed
     * @return true - name is valid, false - name is invalid
     */
    public static boolean isNameValid(@NotNull String name)
    {
        boolean isLengthValid 			= StringUtils.isInRange(name, MIN_NAME_LEN, MAX_NAME_LEN);
        boolean lettersAndSpacesOnly 	= name.matches("[\\p{L}\\s]+");

        return isLengthValid && lettersAndSpacesOnly;
    }



    /**
     * Checks whether provided surname is valid.
     * @param surname - must be trimmed and have redundant spaces removed
     * @return true - surname is valid, false - surname is invalid
     */
    public static boolean isSurnameValid(@NotNull String surname)
    {
        boolean isLengthValid			= StringUtils.isInRange(surname, MIN_SURNAME_LEN, MAX_SURNAME_LEN);
        boolean lettersAndSpacesOnly	= surname.matches("[\\p{L}\\s-]+");

        return isLengthValid && lettersAndSpacesOnly;
    }



    /**
     * Checks whether provided phone number is valid
     * @param phone - must be trimmed and have redundant spaces removed
     * @return true - phone number is valid, false - phone number is invalid
     */
    public static boolean isPhoneValid(@NotNull String phone)
    {
        boolean isLengthValid = StringUtils.isInRange(phone, MIN_PHONE_LEN, MAX_PHONE_LEN);

        if (isLengthValid)
        {
            if (phone.charAt(0) == '+')
                return StringUtils.isNumber(phone.substring(1));
            else
                return StringUtils.isNumber(phone);
        }
        else
            return false;
    }



    /**
     * Checks whether provided address is valid
     * @param address - must be trimmed and have redundant spaces removed
     * @return true - address is valid, false - address is invalid
     */
    public static boolean isAddressValid(@NotNull String address)
    {
        return StringUtils.isInRange(address, MIN_ADDR_LEN, MAX_ADDR_LEN);
    }



    public static boolean isEmailValid(@NotNull String email)
    {
        String emailRegex ="[\\w+-]+(?:\\.[\\w+-]+)*@[\\w+-]+(?:\\.[\\w+-]+)*(?:\\.[a-zA-Z]{2,4})";
        return email.matches(emailRegex);
    }



    /**
     * Checks whether provided password is valid.
     * @param password - password to check
     * @return true - password is valid, false - password is invalid
     */
    public static boolean isPasswordValid(@NotNull String password)
    {
        return StringUtils.isInRange(password.trim(), MIN_PASS_LEN, MAX_PASS_LEN);
    }



    /**
     * Checks whether password is valid and if it is the same as confirmedPassword
     * @param password password to check
     * @param confirmedPassword confirmed password
     * @return true - new password is valid, false - new password is invalid
     */
    public static boolean isNewPasswordValid(@NotNull String password, @NotNull String confirmedPassword)
    {
        return isPasswordValid(password) && password.equals(confirmedPassword);
    }
}
