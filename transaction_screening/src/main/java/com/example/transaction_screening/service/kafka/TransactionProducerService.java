package com.example.transaction_screening.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.kafka.TransactionEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionProducerService {

    private static final  String TOPIC = "transaction_created";

    private final KafkaTemplate<String,TransactionEvent> kafkaTemplate;

    public TransactionProducerService(KafkaTemplate<String,TransactionEvent> kafkaTemplate){
         this.kafkaTemplate=kafkaTemplate;
    }

    public void sendTransactionEvent(TransactionEvent event){
            log.info(
                "Sending transaction event to Kafka. Transaction ID: {}",
                event.getTransactionId()
        );

        kafkaTemplate.send(TOPIC , 
             event.getTransactionId().toString(),
            event
        );

          log.info(
                "Transaction event sent to Kafka. Transaction ID: {}",
                event.getTransactionId()
        );



    }

}
