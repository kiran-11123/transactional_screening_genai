package com.example.transaction_screening.exception.address;

public class AddressNotFoundException extends RuntimeException {
    
    public AddressNotFoundException(String message){
        super(message);
    }
}
