package com.example.transaction_screening.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.annotations.ManyToAny;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String accountNumber;
    
    @Column(nullable = false , precision = 19 ,scale = 2)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private String currency;
    
    @Column(nullable = false)
    private boolean active;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id" ,nullable = false)
    private Customer customer;
   

     @OneToMany(
            mappedBy = "senderAccount",
            fetch = FetchType.LAZY
    )
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(
            mappedBy = "receiverAccount",
            fetch = FetchType.LAZY
    )
    private List<Transaction> receivedTransactions = new ArrayList<>();


}
