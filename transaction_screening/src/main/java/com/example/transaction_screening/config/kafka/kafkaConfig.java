package com.example.transaction_screening.config.kafka;

import java.util.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import com.example.transaction_screening.dto.kafka.EmailRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class kafkaConfig {

    @Bean
    public ProducerFactory<String,EmailRequest> producerFactory(){
          
        Map<String,Object> props = new HashMap<>();

       props.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "localhost:9092"
        );
          props.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class
        );

        props.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class
        );
        
          return new DefaultKafkaProducerFactory<>(props);

    }

    @Bean
    public KafkaTemplate<String,EmailRequest> kafkaTemplate( ProducerFactory<String, EmailRequest> producerFactory){

        return new KafkaTemplate<>(producerFactory);

    }

}
