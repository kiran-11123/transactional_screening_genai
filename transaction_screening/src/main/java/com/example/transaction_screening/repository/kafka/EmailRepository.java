package com.example.transaction_screening.repository.kafka;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.entity.kafka.outBox;

public interface EmailRepository extends JpaRepository<outBox,Long> {

    Optional<outBox> findoutBoxbyIdempotentKey(String idempotentKey);

} 
