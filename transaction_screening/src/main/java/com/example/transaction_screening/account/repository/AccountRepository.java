package com.example.transaction_screening.account.repository;

import java.util.Optional;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.account.entity.Account;

public interface AccountRepository  extends JpaRepository<Account , Long> {

    boolean existsByAccountNumber(String AccountNumber);
    Optional<Account> findAccountByAccountNumber(String AccountNumber);
    List<Account> findByCustomerId(Long customerId);

}
