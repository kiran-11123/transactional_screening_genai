package com.example.transaction_screening.exception.customer;

public class CustomerAlreadyExistsException extends RuntimeException {
    
    public CustomerAlreadyExistsException(String message){
         super(message);
    }
}
