package com.example.pro.service;

import com.example.pro.exception.StudentAlreadyFound;
import com.example.pro.exception.StudentListisEmpty;
import com.example.pro.exception.StudentNotFound;
import com.example.pro.model.Student;
import com.example.pro.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public List<Student> getall() {
        List<Student> students = studentRepo.findAll();
        if (students.isEmpty()) {
            throw new StudentListisEmpty("No students found in the database");
        }
        return students;
    }

    @Override
    public Optional<Student> getById(String adminNo) {
        Optional<Student> student = studentRepo.findById(adminNo);
        if (student.isEmpty()) {
            throw new StudentNotFound("Student with admin number " + adminNo + " not found");
        }
        return student;
    }

    @Override
    public Student add(Student student) {
        if (studentRepo.existsById(student.getAdminNo())) {
            throw new StudentAlreadyFound("Student with admin number " + student.getAdminNo() + " already exists");
        }
        return studentRepo.save(student);
    }

    @Override
    public Student updateStudent(String adminNo, Student student) {
        if (!studentRepo.existsById(adminNo)) {
            throw new StudentNotFound("Student with admin number " + adminNo + " not found");
        }
        
        student.setAdminNo(adminNo); // Ensure we're updating the correct student
        return studentRepo.save(student);
    }

    @Override
    public Student deleteStudent(String adminNo) {
        Optional<Student> student = studentRepo.findById(adminNo);
        if (student.isEmpty()) {
            throw new StudentNotFound("Student with admin number " + adminNo + " not found");
        }
        
        studentRepo.deleteById(adminNo);
        return student.get();
    }
}