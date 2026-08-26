package com.example.transaction_screening.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.kafka.EmailRequest;
import com.example.transaction_screening.entity.kafka.EmailStatus;
import com.example.transaction_screening.entity.kafka.outBox;
import com.example.transaction_screening.repository.kafka.EmailRepository;
import com.example.transaction_screening.service.kafka.EmailSenderService;

import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class kafkaConsumerService {

    private final EmailRepository emailRepository;
    private final EmailSenderService emailSenderService;

    public kafkaConsumerService(EmailRepository emailRepository , EmailSenderService emailSenderService){
        this.emailRepository=emailRepository;
        this.emailSenderService = emailSenderService;
    }

   
    @KafkaListener(topics="user_registered" , groupId = "user-group" , containerFactory = "kafkaListenerContainerFactory")
    public void consume(EmailRequest event){

         log.info(
            "========== KAFKA MESSAGE RECEIVED =========="
        );

        log.info(
            "Email: {}",
            event.getEmail()
        );
          log.info(
            "Processing email request: {}",
            event.getIdempotentKey()
        );
          
        try{

             outBox email = emailRepository.findByIdempotentKey(event.getIdempotentKey()).orElseThrow(()->{
             
            log.warn("Email with Idempotent Key is not present");
              return new RuntimeException(
                "Email with Idempotent Key is missing"
                        );
        });

        emailSenderService.SendEmail(event);

        email.setStatus(EmailStatus.SENT);

        emailRepository.save(email);

         log.info(
                "Email successfully sent to {}",
                event.getEmail()
            );


    



        }
        catch(Exception e){
            log.error(
                "Failed to process registered-user event with idempotent key {}",
                event.getIdempotentKey(),
                e
            );
            throw e;
        }
    }
    

}
