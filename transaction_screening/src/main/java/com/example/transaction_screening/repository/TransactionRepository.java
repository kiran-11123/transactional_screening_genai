package com.example.transaction_screening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>  {

}
