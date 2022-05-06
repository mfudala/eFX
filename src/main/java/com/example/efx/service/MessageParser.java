package com.example.efx.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.efx.model.Price;

public class MessageParser {
	private static final String DATE_TIME_FORMAT = "dd-MM-yyyyHH:mm:ss:SSS";

	public Price mapMessageToPrice(final String[] messageSplit) {
		return new Price(
				Long.parseLong(messageSplit[0]),
				messageSplit[1],
				new BigDecimal(messageSplit[2]),
				new BigDecimal(messageSplit[3]),
				LocalDateTime.parse(messageSplit[4], DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
		);
	}
}
