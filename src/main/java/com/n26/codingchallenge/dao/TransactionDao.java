/**
 * 
 */
package com.n26.codingchallenge.dao;

import java.util.List;

import com.n26.codingchallenge.dao.model.Transaction;

public interface TransactionDao {
	   Transaction save(Transaction transaction);
	   List<Double> findAll(long fromTimestamp);
}
