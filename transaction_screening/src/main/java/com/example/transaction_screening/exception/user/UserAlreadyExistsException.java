package com.example.transaction_screening.exception.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
         super(message);
    }

}
