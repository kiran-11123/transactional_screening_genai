package com.example.transaction_screening.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.transaction_screening.entity.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionResponse {

    private Long id;

    private Long senderAccountId;
    private Long receiverAccountId;

    private LocalDateTime createdAt;
    private BigDecimal amount;

    private TransactionStatus status;

}
