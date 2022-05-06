package com.example.efx.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Price {
	private final long id;
	private final String instrumentName;
	private BigDecimal bidPrice;
	private BigDecimal askPrice;
	private final LocalDateTime timestamp;

	public Price(final long id, final String instrumentName, final BigDecimal bidPrice, final BigDecimal askPrice,  final LocalDateTime timestamp) {
		this.id = id;
		this.instrumentName = instrumentName;
		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.timestamp = timestamp;
		applyCommission();
	}

	private void applyCommission() {
		bidPrice = bidPrice.multiply(getBidCommissionMultiplier());
		askPrice = askPrice.multiply(getAskCommissionMultiplier());
	}

	public long getId() {
		return this.id;
	}

	public String getInstrumentName() {
		return this.instrumentName;
	}

	public String getAskPrice() {
		return this.askPrice.toString();
	}

	public String getBidPrice() {
		return this.bidPrice.toString();
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	private BigDecimal getBidCommissionMultiplier() {
		return BigDecimal.ONE.subtract(BigDecimal.valueOf(Commission.STANDARD.getValue()));
	}

	private BigDecimal getAskCommissionMultiplier() {
		return BigDecimal.ONE.add(BigDecimal.valueOf(Commission.STANDARD.getValue()));
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o)
			return true;

		if(o == null || getClass() != o.getClass())
			return false;

		final Price that = (Price) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("instrumentName", instrumentName)
				.append("bidPrice", bidPrice)
				.append("askPrice", askPrice)
				.append("timestamp", timestamp)
				.toString();
	}
}
