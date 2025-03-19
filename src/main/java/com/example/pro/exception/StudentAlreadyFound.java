package com.example.pro.exception;

public class StudentAlreadyFound extends RuntimeException {
    public StudentAlreadyFound(String message) {
        super(message);
    }
}