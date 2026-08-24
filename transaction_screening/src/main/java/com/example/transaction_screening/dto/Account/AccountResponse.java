package com.example.transaction_screening.dto.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private String currency;

    private boolean active;

    private LocalDateTime createdAt;

    private Long customerId;

}
