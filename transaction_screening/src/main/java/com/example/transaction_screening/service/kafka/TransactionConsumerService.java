package com.example.transaction_screening.service.kafka;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.kafka.TransactionEvent;

@Service
@Slf4j
public class TransactionConsumerService {


    @KafkaListener(  topics = "transaction_created",
            groupId = "transaction-screening-group")

    public void consumer(TransactionEvent event){
        log.info("======================================");
        log.info("TRANSACTION EVENT RECEIVED");
        log.info("Transaction ID: {}", event.getTransactionId());
        log.info("Sender Account: {}", event.getSenderAccountId());
        log.info("Receiver Account: {}", event.getReceiverAccountId());
        log.info("Amount: {}", event.getAmount());
        log.info("Status: {}", event.getStatus());
        log.info("======================================");
    }

}
