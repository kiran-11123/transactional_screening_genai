package com.example.transaction_screening.account.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.ManyToAny;

import com.example.transaction_screening.customer.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts" , indexes = {
    @Index(name ="idx_account_number" , columnList = "account_number")
})
@Data
public class Account {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "account_number" , nullable = false , unique = true , length = 20)
    private String accountNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
   
    @Column(nullable = false, precision = 19,scale = 2)
    private BigDecimal balance;
    
    @Column(nullable = false , length = 3)
    private String currency;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate(){
          LocalDateTime now = LocalDateTime.now();

          createdAt = now;
          updatedAt = now;
    }
    
    @PreUpdate
    protected void onUpdate(){
         updatedAt = LocalDateTime.now();
    }

}
