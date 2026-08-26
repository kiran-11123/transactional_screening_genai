package com.example.transaction_screening.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.kafka.EmailRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class kafkaProducerService {
        
    private KafkaTemplate<String,EmailRequest> kafkaTemplate;

    public kafkaProducerService(KafkaTemplate<String,EmailRequest> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void PublishRegisteredUser(EmailRequest request){
        log.info("Sending the event into producer");
        kafkaTemplate.send(
            "user_registered",
            request.getIdempotentKey(),
            request
        ).whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to publish registered-user event", exception);
                return;
            }

            log.info(
                    "Registered-user event published to partition {}, offset {}",
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }
}
