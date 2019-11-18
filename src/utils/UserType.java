package utils;

public enum UserType {
	UNREGISTERED, BUYER, SELLER, ADMIN;

	public static UserType getInstance(String typeStr) {
		if (typeStr.equals("UNREGISTERED"))
			return UNREGISTERED;
		else if (typeStr.equals("BUYER"))
			return BUYER;
		else if (typeStr.equals("SELLER"))
			return SELLER;
		else if (typeStr.equals("ADMIN"))
			return ADMIN;
		else
			throw new IllegalArgumentException("No such user type: " + typeStr);
	}
}
