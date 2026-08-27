package com.example.transaction_screening.exception.user;


public class UserNotFoundException extends RuntimeException {
    
    public  UserNotFoundException(String message){
        super(message);
    }
}
