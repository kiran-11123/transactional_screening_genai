package com.example.transaction_screening.dto.Customer;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    
    
    private String name;
    private String email;
    private LocalDateTime createdAt;


}
