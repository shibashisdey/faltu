package com.example.pro.exception;

public class StudentListisEmpty extends RuntimeException {
    public StudentListisEmpty(String message) {
        super(message);
    }
}