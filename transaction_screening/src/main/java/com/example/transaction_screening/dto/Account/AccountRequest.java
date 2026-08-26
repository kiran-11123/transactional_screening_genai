package com.example.transaction_screening.dto.Account;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    
  
    private String accountNumber;
    
   @NotNull(message = "Balance is required")
    @DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "Balance must be greater than zero"
    )
    private BigDecimal balance;
   
    @NotBlank(message = "currency is required")
    private String currency;

   
}
