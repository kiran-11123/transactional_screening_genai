package com.example.transaction_screening.exception.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message){
        super(message);
    }

}
