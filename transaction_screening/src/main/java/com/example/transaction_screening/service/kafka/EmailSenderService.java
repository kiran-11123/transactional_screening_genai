package com.example.transaction_screening.service.kafka;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.transaction_screening.dto.kafka.EmailRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender javaMailSender){
        this.mailSender = javaMailSender;
    }

    public void SendEmail(EmailRequest request){
          log.info("Sending welcome email for  ${}", request.getEmail());

          SimpleMailMessage message = new SimpleMailMessage();

          message.setTo(request.getEmail());
          message.setSubject("Welcome");
          message.setText( "Hello, your registration was successful.");

          mailSender.send(message);
          log.info("Welcome mail sent successfully for ${}",request.getEmail());
    }

}
