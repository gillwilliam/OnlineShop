package utils;

import java.math.BigDecimal;

public class Price extends BigDecimal {

	public Price(int mainPart, int fractionalPart, String currency) {
		super(mainPart + fractionalPart / 100D);

	}
}
