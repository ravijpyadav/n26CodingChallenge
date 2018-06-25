/**
 * 
 */
package com.n26.codingchallenge.service;

import com.n26.codingchallenge.dao.model.TransactionSummary;

public interface TransactionService {
	public int addTransaction(Double amount, Long timestamp);
	public TransactionSummary getTransactionSummary();
	public void populateStatistics();
}
