package com.example.pro.exception;

public class AdminDoNotExist extends RuntimeException {
    public AdminDoNotExist(String message) {
        super(message);
    }
}