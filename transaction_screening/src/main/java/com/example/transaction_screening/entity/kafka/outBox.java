package com.example.transaction_screening.entity.kafka;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.transaction_screening.entity.kafka.EmailStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outbox", indexes = {
    @Index(name = "idempotentKey", columnList = "IdempotentKey")
})
public class outBox {

@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long Id;
    
    @Column(nullable = false , unique = true)
    private String IdempotentKey;
    
    @Column(nullable = false , unique =  true)
    private String email ;
    
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
