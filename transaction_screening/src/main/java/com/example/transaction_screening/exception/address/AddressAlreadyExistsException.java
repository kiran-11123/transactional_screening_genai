package com.example.transaction_screening.exception.address;

public class AddressAlreadyExistsException extends RuntimeException {

    public AddressAlreadyExistsException(String message) {
        super(message);
    }
}
