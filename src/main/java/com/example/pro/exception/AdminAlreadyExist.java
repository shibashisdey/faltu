package com.example.pro.exception;

public class AdminAlreadyExist extends RuntimeException {
    public AdminAlreadyExist(String message) {
        super(message);
    }
}
