package com.example.transaction_screening.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import com.example.transaction_screening.entity.Account;

@Entity
@Builder
@Table(name="customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    
    @Column(nullable = false)
    private String name;
     
    @Column(nullable = false , unique = true)
    private String email;
   
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
   
@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL , orphanRemoval = true)
   private List<Account> accounts = new ArrayList<>();


}
