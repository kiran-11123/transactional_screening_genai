package com.example.transaction_screening.dto.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private String currency;

    private boolean active;

    private LocalDateTime createdAt;

    private Long customerId;

}
