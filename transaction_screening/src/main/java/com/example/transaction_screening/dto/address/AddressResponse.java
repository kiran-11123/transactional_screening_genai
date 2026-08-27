package com.example.transaction_screening.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
     
    private String username;
    private String city;
    private String country;
}
