/**
 * 
 */
package com.n26.codingchallenge.service;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.n26.codingchallenge.dao.TransactionDao;
import com.n26.codingchallenge.dao.model.Transaction;
import com.n26.codingchallenge.dao.model.TransactionSummary;

public class TransactionServiceImpl implements TransactionService {
	 private TransactionDao transactionDao;
	 private TransactionSummary transactionSummary;
	 private ReentrantLock lock = new ReentrantLock();
	@Override
	public int addTransaction(Double amount, Long timestamp) {
		int returnCode = 204;
		Date currentDate = new Date();
		Transaction transaction = new Transaction(amount, timestamp, currentDate, currentDate);
		transactionDao.save(transaction);
		
		 if (timestamp > (currentDate.getTime() -60*1000)) { // try to populate summary as current transaction is in last 60 seconds
			 lock.lock();
			 populateStatistics();
			returnCode = 201;
			lock.unlock();
		}
		return returnCode;
	}

	/**
	 * 
	 */
	public void populateStatistics() {
		List<Double> transactionAmounts = transactionDao.findAll(60 * 1000L);
		if (!transactionAmounts.isEmpty()) {
			DoubleSummaryStatistics doubleSummaryStatistics = transactionAmounts.stream().mapToDouble((x) -> x)
					.summaryStatistics();
			transactionSummary = new TransactionSummary(doubleSummaryStatistics.getSum(),
					doubleSummaryStatistics.getAverage(), doubleSummaryStatistics.getMax(),
					doubleSummaryStatistics.getMin(), doubleSummaryStatistics.getCount());
		}
	}

	@Override
	public TransactionSummary getTransactionSummary() {
		TransactionSummary transactionSummary = this.transactionSummary; // create a local reference
		if(transactionSummary == null) return null;
		
		return new TransactionSummary(transactionSummary.getSum(), transactionSummary.getAvg(), transactionSummary.getMax(), transactionSummary.getMin(),
				transactionSummary.getCount());
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

}
