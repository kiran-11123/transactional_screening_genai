package com.example.transaction_screening.exception.customer;


public class CustomerNotFoundException extends RuntimeException {
      
    public CustomerNotFoundException(String message){
        super(message);
    }

}
