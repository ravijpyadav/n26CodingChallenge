/**
 * 
 */
package com.n26.codingchallenge.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.n26.codingchallenge.dao.model.Transaction;
import com.n26.codingchallenge.dao.repository.TransactionRepository;

public class TransactionDaoImpl implements TransactionDao {
	@Autowired
    private TransactionRepository transactionRepository;
	@Override
	public Transaction save(Transaction transaction) {
		return this.transactionRepository.save(transaction);
	}

	@Override
	public List<Double> findAll(long fromTimestamp) {
		 return transactionRepository.findAll(fromTimestamp);
	}

}
