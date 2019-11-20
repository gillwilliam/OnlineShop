package utils;

import java.math.BigDecimal;

public class Price extends BigDecimal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Price(int mainPart, int fractionalPart, String currency) {
		super(mainPart + fractionalPart / 100D);

	}
}
