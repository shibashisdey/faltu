package com.example.pro;

import com.example.pro.model.Student;
import com.example.pro.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Student Controller", description = "CRUD operations for students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    @Operation(summary = "Get all students", description = "Returns a list of all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of students retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No students found")
    })
    public ResponseEntity<List<Student>> getall() {
        List<Student> students = studentService.getall();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get/{adminNo}")
    @Operation(summary = "Get student by admin number", description = "Returns a student by their admin number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Student> getById(@PathVariable("adminNo") String adminNo) {
        Student student = studentService.getById(adminNo).orElseThrow();
        return ResponseEntity.ok(student);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new student", description = "Adds a new student to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "409", description = "Student already exists")
    })
    public ResponseEntity<Student> add(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/update/{adminNo}")
    @Operation(summary = "Update student", description = "Updates an existing student's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Student> update(
            @PathVariable("adminNo") String adminNo,
            @Valid @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(adminNo, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/delete/{adminNo}")
    @Operation(summary = "Delete student", description = "Deletes a student from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Student> deleteStudent(@PathVariable("adminNo") String adminNo) {
        Student deletedStudent = studentService.deleteStudent(adminNo);
        return ResponseEntity.ok(deletedStudent);
    }
}