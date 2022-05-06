package com.example.efx.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PriceTest {

	@ParameterizedTest
	@MethodSource
	void shouldApplyCommission(long id, String instrumentName, BigDecimal inputBidPrice, BigDecimal inputAskPrice, LocalDateTime timestamp){
		// given
		double commission = Commission.STANDARD.getValue();

		// when
		Price priceUnderTest = new Price(id, instrumentName, inputBidPrice, inputAskPrice, timestamp);

		// then
		String bidPriceWithCommission = priceUnderTest.getBidPrice();
		String askPriceWithCommission = priceUnderTest.getAskPrice();

		Assertions.assertEquals(bidPriceWithCommission, inputBidPrice.multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(commission))).toString());
		Assertions.assertEquals(askPriceWithCommission, inputAskPrice.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(commission))).toString());
	}

	private static Stream<Arguments> shouldApplyCommission() {
		return Stream.of(
				Arguments.of(1L, "EUR/USD", BigDecimal.valueOf(1.100), BigDecimal.valueOf(1.200), null),
				Arguments.of(2L, "EUR/GBP", BigDecimal.valueOf(1.300), BigDecimal.valueOf(1.500), null)
		);
	}
}