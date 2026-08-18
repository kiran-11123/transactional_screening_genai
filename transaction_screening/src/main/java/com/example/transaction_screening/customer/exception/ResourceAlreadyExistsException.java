package com.example.transaction_screening.customer.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message){
        super(message);
    }

}
