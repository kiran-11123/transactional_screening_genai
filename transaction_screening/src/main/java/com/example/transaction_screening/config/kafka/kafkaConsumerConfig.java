package com.example.transaction_screening.config.kafka;
import com.example.transaction_screening.dto.kafka.EmailRequest;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class kafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String,EmailRequest> consumerFactory(){
           
        Map<String,Object> props = new HashMap();

         props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "localhost:9092"
        );

        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            "user-group"
        );

        props.put(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
            "earliest"
        );

        props.put(
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
            false
        );

          JsonDeserializer<EmailRequest> jsonDeserializer =
                new JsonDeserializer<>(EmailRequest.class);

        jsonDeserializer.addTrustedPackages(
            "com.example.email_kafka.dto"
        );

        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            jsonDeserializer
        );


    }



      @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, EmailRequest>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, EmailRequest> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, EmailRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;

            }

}
