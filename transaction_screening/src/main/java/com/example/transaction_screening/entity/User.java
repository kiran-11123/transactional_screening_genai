package com.example.transaction_screening.entity;

import java.time.LocalDateTime;
import com.example.transaction_screening.entity.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "users" , indexes ={
   @Index(name="email" , columnList = "email")
} )
public class User {
    
   @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    
    @Column(unique = true , nullable = false)
    private String username;

    @Column(unique =  true, nullable = false)
    private String email;

      @Column(nullable = false)
    private String password;

     @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

      @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    
     @OneToOne(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

}
