package com.example.transaction_screening.dto.kafka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEvent {

    private Long transactionId;

    private Long senderAccountId;

    private Long receiverAccountId;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    private String status;

    private String senderHouseNumber;
    private String senderStreet;
    private String senderCity;
    private String senderState;
    private String senderPostalCode;
    private String senderCountry;

    private String receiverHouseNumber;
    private String receiverStreet;
    private String receiverCity;
    private String receiverState;
    private String receiverPostalCode;
    private String receiverCountry;
}