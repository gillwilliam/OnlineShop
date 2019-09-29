package test;

import org.junit.Test;

import utils.UserDataValidator;



class UserDataValidatorTest {


    @Test
    void isNameValid()
    {
        String invalidName1 = "";
        String invalidName2 = "a";
        String invalidName3 = "1";
        String invalidName4 = "a2";
        String invalidName5 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        String validName1 = "ab";
        String validName2 = "Walętyna";
        String validName3 = "Grzegorz";
        String validName4 = "Jan Sebastian";

        assert(!UserDataValidator.isNameValid(invalidName1));
        assert(!UserDataValidator.isNameValid(invalidName2));
        assert(!UserDataValidator.isNameValid(invalidName3));
        assert(!UserDataValidator.isNameValid(invalidName4));
        assert(!UserDataValidator.isNameValid(invalidName5));
        assert(UserDataValidator.isNameValid(validName1));
        assert(UserDataValidator.isNameValid(validName2));
        assert(UserDataValidator.isNameValid(validName3));
        assert(UserDataValidator.isNameValid(validName4));
    }



    @Test
    void isSurnameValid()
    {
        String invalidSurname1 = "";
        String invalidSurname2 = "b";
        String invalidSurname3 = "1";
        String invalidSurname4 = "a2";
        String invalidSurname5 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        String validSurname1 = "ab";
        String validSurname2 = "ścierwo";
        String validSurname3 = "Brzęczyszczykiewicz";
        String validSurname4 = "Chinewicz-Banach";

        assert(!UserDataValidator.isSurnameValid(invalidSurname1));
        assert(!UserDataValidator.isSurnameValid(invalidSurname2));
        assert(!UserDataValidator.isSurnameValid(invalidSurname3));
        assert(!UserDataValidator.isSurnameValid(invalidSurname4));
        assert(!UserDataValidator.isSurnameValid(invalidSurname5));
        assert(UserDataValidator.isSurnameValid(validSurname1));
        assert(UserDataValidator.isSurnameValid(validSurname2));
        assert(UserDataValidator.isSurnameValid(validSurname3));
        assert(UserDataValidator.isSurnameValid(validSurname4));
    }



    @Test
    void isPhoneValid()
    {
        String invalidPhone1 = "asdfasfasfsafasfassfa";
        String invalidPhone2 = "123";
        String invalidPhone3 = "++123456";
        String invalidPhone4 = "+1221bb44";
        String invalidPhone5 = "+a322423421";
        String invalidPhone6 = "+1111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String validPhone1 = "123456789";
        String validPhone2 = "+34754432111";

        assert(!UserDataValidator.isPhoneValid(invalidPhone1));
        assert(!UserDataValidator.isPhoneValid(invalidPhone2));
        assert(!UserDataValidator.isPhoneValid(invalidPhone3));
        assert(!UserDataValidator.isPhoneValid(invalidPhone4));
        assert(!UserDataValidator.isPhoneValid(invalidPhone5));
        assert(!UserDataValidator.isPhoneValid(invalidPhone6));
        assert(UserDataValidator.isPhoneValid(validPhone1));
        assert(UserDataValidator.isPhoneValid(validPhone2));
    }



    @Test
    void isAddressValid()
    {
        String invalidAddress1 = "sadf";
        String invalidAddress2 = "asdfffffffffffffffffffffffffffffffff9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";

        String validAddress	= "ul. Marszałka Józefa Piłsudskiego 97, 50-016 Wrocław, Polska";

        assert(!UserDataValidator.isAddressValid(invalidAddress1));
        assert(!UserDataValidator.isAddressValid(invalidAddress2));
        assert(UserDataValidator.isAddressValid(validAddress));
    }



    @Test
    void isEmailValid()
    {
        String invalidEmail1 = "";
        String invalidEmail2 = "asdfads";
        String invalidEmail3 = "@j.com";
        String invalidEmail4 = "......@gmail.com";
        String invalidEmail5 = ":::::;;@:::::.;;;";
        String invalidEmail6 = "ąćgrząźż@gmail.com";

        String validEmail1 = "aaaaa@aaaa.com";
        String validEmail2 = "web.technology@gmail.com";

        assert(!UserDataValidator.isEmailValid(invalidEmail1));
        assert(!UserDataValidator.isEmailValid(invalidEmail2));
        assert(!UserDataValidator.isEmailValid(invalidEmail3));
        assert(!UserDataValidator.isEmailValid(invalidEmail4));
        assert(!UserDataValidator.isEmailValid(invalidEmail5));
        assert(!UserDataValidator.isEmailValid(invalidEmail6));
        assert(UserDataValidator.isEmailValid(validEmail1));
        assert(UserDataValidator.isEmailValid(validEmail2));
    }



    @Test
    void isPasswordValid()
    {
        String invalidPassword1 = "asdf";
        String invalidPassword2 = "                                 ";
        String invalidPassword3 = "sadffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";

        String validPassword1 = "asdfdsadsfasdfasf";
        String validPassword2 = "asśążć1233*";

        assert(!UserDataValidator.isPasswordValid(invalidPassword1));
        assert(!UserDataValidator.isPasswordValid(invalidPassword2));
        assert(!UserDataValidator.isPasswordValid(invalidPassword3));
        assert(UserDataValidator.isPasswordValid(validPassword1));
        assert(UserDataValidator.isPasswordValid(validPassword2));
    }



    @Test
    void isNewPasswordValid()
    {
        isPasswordValid();
        assert(UserDataValidator.isNewPasswordValid("abcdefghijk", "abcdefghijk"));
        assert(!UserDataValidator.isNewPasswordValid("abcdefghijk", "abcdefghijkkk"));
    }
}