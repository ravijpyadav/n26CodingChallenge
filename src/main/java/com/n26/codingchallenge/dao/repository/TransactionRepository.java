/**
 * 
 */
package com.n26.codingchallenge.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.n26.codingchallenge.dao.model.Transaction;

public interface TransactionRepository  extends CrudRepository<Transaction, Long> {

    @Query("Select t.amount FROM Transaction t WHERE t.timestamp >= :fromTimestamp")
    List<Double> findAll(@Param("fromTimestamp") long fromTimestamp);
}
