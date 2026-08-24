package com.example.transaction_screening.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.transaction_screening.entity.TransactionStatus;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long senderAccountId;
    
    @Column(nullable = false)
    private Long receiverAccountId;

        @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

   
    @Column(nullable = false)
    private LocalDateTime createdAt;
 
}
