package com.example.efx.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageParserTest {
	private final MessageParser messageParser = new MessageParser();

	@Test
	void shouldMapMessageToPrice() {
		// given
		var trimmedMessage = getMessage().replaceAll("\\s+", "");
		var splitMessage = trimmedMessage.split(",");

		// when
		var price = messageParser.mapMessageToPrice(splitMessage);

		// then
		Assertions.assertEquals(108L, price.getId());
		Assertions.assertEquals("GBP/USD", price.getInstrumentName());
		Assertions.assertEquals("1.1988000", price.getBidPrice());
		Assertions.assertEquals("1.3013000", price.getAskPrice());
		Assertions.assertEquals(LocalDateTime.parse("2020-06-01T12:01:02.002"), price.getTimestamp());
	}

	private String getMessage() {
		return "108, GBP/USD, 1.2000,1.3000,01-06-2020 12:01:02:002";
	}
}