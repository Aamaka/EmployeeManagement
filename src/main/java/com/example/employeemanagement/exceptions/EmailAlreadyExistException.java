package com.example.employeemanagement.exceptions;

public class EmailAlreadyExist extends RuntimeException {
    public EmailAlreadyExist(String message) {
        super(message);
    }
}
