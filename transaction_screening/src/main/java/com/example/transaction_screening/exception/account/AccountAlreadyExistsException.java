package com.example.transaction_screening.exception.account;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String message){
        super(message);
    }

}
