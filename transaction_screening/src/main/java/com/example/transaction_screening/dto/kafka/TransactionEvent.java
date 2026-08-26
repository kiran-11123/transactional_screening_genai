package com.example.transaction_screening.dto.kafka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransactionEvent {
      
    private Long transactionId;

      private Long senderAccountId;

    private Long receiverAccountId;

    private BigDecimal amount;

       private LocalDateTime createdAt;

    private String status;

}
