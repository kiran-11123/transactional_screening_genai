package com.example.transaction_screening.dto.Account;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotBlank(message = "Balance is required")
    @Positive(message =  "Balance must be greater than zero")
    private BigDecimal balance;
   
    @NotBlank(message = "currency is required")
    private String currency;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
