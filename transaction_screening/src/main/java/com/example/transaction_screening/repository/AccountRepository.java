package com.example.transaction_screening.repository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.entity.Account;

public interface AccountRepository extends JpaRepository<Account , Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
 

    boolean existsByAccountNumber(String accountNumber);
}
