package com.example.transaction_screening.customer.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
  private String firstName;
  private String lastName;
  private String email;
        private String phone;
        private LocalDateTime createdAt;
 
}
