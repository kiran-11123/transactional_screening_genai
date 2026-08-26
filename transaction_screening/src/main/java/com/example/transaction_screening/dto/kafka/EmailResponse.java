package com.example.transaction_screening.dto.kafka;

import lombok.Data;

@Data
public class EmailResponse {
    
     private String email;
     private Enum status;
}
