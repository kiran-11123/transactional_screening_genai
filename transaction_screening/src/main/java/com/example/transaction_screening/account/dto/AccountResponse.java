package com.example.transaction_screening.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.transaction_screening.account.entity.AccountStatus;
import com.example.transaction_screening.account.entity.AccountType;

import lombok.Data;

@Data
public class AccountResponse {
      
    private Long id;
    private String accountNumber;

    private AccountType accountType;
    private BigDecimal balance;
    private String Currency;

    private AccountStatus accountStatus;
     private Long customerId;

    private LocalDateTime createdAt;
}
