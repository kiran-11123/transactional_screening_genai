package com.example.transaction_screening.customer.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer , Long>{
     
    boolean existsByEmail(String email);
}
