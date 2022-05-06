package com.example.efx.service;

import java.util.Arrays;

import com.example.efx.model.Price;
import com.example.efx.resource.MarketPricePublisherRs;

public class MarketPriceSubscriptionServiceImpl implements MarketPriceSubscriptionService {
	private final MarketPricePublisherRs marketPricePublisherRs;
	private final MessageParser messageParser;

	public MarketPriceSubscriptionServiceImpl(final MarketPricePublisherRs marketPricePublisherRs, final MessageParser messageParser) {
		this.marketPricePublisherRs = marketPricePublisherRs;
		this.messageParser = messageParser;
	}

	@Override
	public void onMessage(final String message) {
		processIncomingMessage(message);
	}

	private void processIncomingMessage(final String message) {
		var lineItem = message.split("\n");

		Arrays.stream(lineItem)
				.map(line -> line.replaceAll("\\s+",""))
				.map(trimmedLine -> trimmedLine.split(","))
				.map(messageParser::mapMessageToPrice)
				.forEach(this::publishPrice);
	}

	private void publishPrice(final Price price) {
		marketPricePublisherRs.publishPrice(price);
	}
}
