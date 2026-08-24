package com.example.transaction_screening.dto.transaction;

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
public class TransactionRequest {

    
     @NotNull(message = "Sender account ID is required")
    private Long senderAccountId;

    @NotNull(message = "Receiver account ID is required")
    private Long receiverAccountId;
    
    @NotNull(message = "Transaction amount is required")
    @DecimalMin(
        value = "0.01",
        inclusive = true,
        message = "Transaction amount must be greather than zero"
    )
    private BigDecimal amount;


}
