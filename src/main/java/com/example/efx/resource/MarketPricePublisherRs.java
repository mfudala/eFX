package com.example.efx.resource;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.efx.model.Price;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MarketPricePublisherRs {
	private static final Logger LOGGER = LogManager.getLogger(MarketPricePublisherRs.class);
	private final HttpClient httpClient;

	public MarketPricePublisherRs(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void publishPrice(Price price) {
		var json = toJSON(price);
		var postRequest = preparePostRequest(json);

		try {
			httpClient.execute(postRequest);
		} catch(IOException e) {
			LOGGER.warn("Request with payload: {} could not be sent", json);
		}
	}

	private String toJSON(Price price) {
		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			return objectMapper.writeValueAsString(price);
		} catch(IOException e) {
			LOGGER.warn("Could not parse object: {} to json format.", price);
			return null;
		}
	}

	private HttpPost preparePostRequest(final String json) {
		var postRequest = new HttpPost("v1/market-price-feed/prices");
		var entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		postRequest.setEntity(entity);
		return postRequest;
	}
}
