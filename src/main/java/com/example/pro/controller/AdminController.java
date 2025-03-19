package com.example.pro.controller;

import com.example.pro.exception.AdminAlreadyExist;
import com.example.pro.exception.AdminDoNotExist;
import com.example.pro.exception.InvalidPassword;
import com.example.pro.model.Admin;
import com.example.pro.model.dto.AdminLoginRequest;
import com.example.pro.model.dto.AuthResponse;
import com.example.pro.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "APIs for admin authentication")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    @Operation(summary = "Register a new admin", description = "Creates a new admin account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin registered successfully"),
            @ApiResponse(responseCode = "409", description = "Admin already exists",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> register(@Valid @RequestBody Admin admin) {
        try {
            Admin registeredAdmin = adminService.registration(admin);
            return ResponseEntity.ok(registeredAdmin);
        } catch (AdminAlreadyExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Admin login", description = "Authenticates admin and returns JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> login(@Valid @RequestBody AdminLoginRequest loginRequest) {
        try {
            String token = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AdminDoNotExist e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidPassword e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}