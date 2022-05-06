package com.example.efx.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.efx.model.Price;

@ExtendWith(MockitoExtension.class)
class MarketPricePublisherRsTest {

	@Mock
	private HttpClient httpClient;

	private MarketPricePublisherRs marketPricePublisherRs;

	@BeforeEach
	void setUp() {
		marketPricePublisherRs = new MarketPricePublisherRs(httpClient);
	}

	@Test
	void shouldPublishPrice() throws IOException {
		// given
		var price = getPrice();

		// when
		marketPricePublisherRs.publishPrice(price);

		// then
		Mockito.verify(httpClient, times(1)).execute(any(HttpPost.class));
	}

	private Price getPrice(){
		return new Price(1L, "EUR/USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"), LocalDateTime.now());
	}
}