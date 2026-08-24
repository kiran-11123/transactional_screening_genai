package com.example.transaction_screening.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transaction_screening.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer , Long> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

}
