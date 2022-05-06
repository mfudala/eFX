package com.example.efx.service;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.efx.resource.MarketPricePublisherRs;

@ExtendWith(MockitoExtension.class)
class MarketPriceSubscriptionServiceImplTest {

	@Mock
	private MarketPricePublisherRs marketPricePublisherRs;

	@Mock
	private MessageParser messageParser;

	private MarketPriceSubscriptionServiceImpl marketPriceSubscriptionService;

	@BeforeEach
	public void setUp() {
		marketPriceSubscriptionService = new MarketPriceSubscriptionServiceImpl(marketPricePublisherRs, messageParser);
	}

	@Test
	void shouldProcessMessage() {
		// given
		var incomingMessage = getIncomingMessage();

		// when
		marketPriceSubscriptionService.onMessage(incomingMessage);

		// then
		verify(marketPricePublisherRs, times(3)).publishPrice(any());
	}

	private String getIncomingMessage() {
		return "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n"
				+ "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n"
				+ "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110";
	}
}