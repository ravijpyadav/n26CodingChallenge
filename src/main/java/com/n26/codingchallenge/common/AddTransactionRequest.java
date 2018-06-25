/**
 * 
 */
package com.n26.codingchallenge.common;

import javax.validation.constraints.NotNull;

public class AddTransactionRequest {
	@NotNull
	private Double amount;

	@NotNull
	private Long timestamp;
	public AddTransactionRequest(Double amount, Long timestamp){
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction : {" + "Amount: " + this.amount + "Timestamp: " + this.timestamp + "}";
	}
}
