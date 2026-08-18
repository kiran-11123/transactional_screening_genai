package com.example.transaction_screening.customer.exception;

public class ResourceNotFoundException extends RuntimeException {
       
    public ResourceNotFoundException(String message){
         super(message);
    }
}
