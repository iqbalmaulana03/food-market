package com.foodmarket.foodmarket.repository;

import com.foodmarket.foodmarket.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional
    @Query(value = "select t.id, t.food_id , t.user_id , t.quantity, t.total, t.status from transactions t LIMIT 10 OFFSET 10",
    nativeQuery = true)
    List<Object[]> getTransactionLimit();

    @Transactional
    @Query(value = "select t.id, t.food.name, t.user.name, t.quantity, t.total, t.status from Transaction t where t.id = :id")
    List<Object[]> transactionById(@Param("id") Long id);

}
