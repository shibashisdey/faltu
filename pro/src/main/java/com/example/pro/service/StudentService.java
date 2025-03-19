package com.example.pro.service;

import com.example.pro.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getall();
    Optional<Student> getById(String adminNo);
    Student add(Student student);
    Student updateStudent(String adminNo, Student student);
    Student deleteStudent(String adminNo);
}