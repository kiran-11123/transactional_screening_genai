package com.example.transaction_screening.account.dto;
import com.example.transaction_screening.account.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAccountRequest {

   
    @NotBlank(message =  "Customer Id is required")
    private Long CustomerId;
    
    @NotBlank(message =  "Account Type is Required")
    private AccountType accountType;


    @NotNull(message = "Currency is required")
    @Pattern(
        regexp = "^[A-Z]{3}$",
        message = "Currency must be a valid 3-letter currency code"
    )
    private String currency;
    
}
