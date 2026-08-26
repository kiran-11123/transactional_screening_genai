package com.example.transaction_screening.service.kafka;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.entity.kafka.EmailStatus;
import com.example.transaction_screening.entity.kafka.outBox;
import com.example.transaction_screening.dto.kafka.EmailRequest;
import com.example.transaction_screening.dto.kafka.EmailResponse;
import com.example.transaction_screening.repository.kafka.EmailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendEmailService {

    private final EmailRepository emailRepository;
    private final kafkaProducerService kafkaproducerService;

    public SendEmailService(EmailRepository emailRepository , kafkaProducerService  kafkaproducerService){
         this.emailRepository=emailRepository;
         this.kafkaproducerService = kafkaproducerService;
    }

    public EmailResponse sendEmail(EmailRequest request){

        log.info("Entered into Send email service for email ${}" , request.getEmail());

        try{ 

             
            Optional<outBox> checkEmail = emailRepository.findByIdempotentKey(request.getIdempotentKey());

            if(checkEmail.isPresent()){
                outBox existingEmail = checkEmail.get();

    log.info(
            "Email already exists in outbox. Email: {}, Status: {}",
            existingEmail.getEmail(),
            existingEmail.getStatus()
    );

    return EmailResponse.builder()
            .email(existingEmail.getEmail())
            .status(existingEmail.getStatus())
            .build();
            }

                outBox emailOutbox = outBox.builder()
                    .email(request.getEmail())
                    .idempotentKey(request.getIdempotentKey())
                    .status(EmailStatus.PROCESSING)
                    .createdAt(LocalDateTime.now())
                    .build();

                outBox savedEmailOutBox = emailRepository.save(emailOutbox);

                kafkaproducerService.PublishRegisteredUser(request);
                log.info("Event sent to kafka producer in EmailService");

                return EmailResponse.builder().email(request.getEmail()).status(savedEmailOutBox.getStatus()).build();


            




        }
        catch(Exception e){
             log.warn("Error while sending to Kafka ${} " , e);
             throw new RuntimeException("Error while sending to Kafka ${} " , e);
        }
         
    }



}
