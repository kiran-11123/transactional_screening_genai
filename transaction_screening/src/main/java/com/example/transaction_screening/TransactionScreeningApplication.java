package com.example.transaction_screening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class TransactionScreeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionScreeningApplication.class, args);
	}

}
