package com.example.pro.service;

import com.example.pro.exception.AdminAlreadyExist;
import com.example.pro.exception.AdminDoNotExist;
import com.example.pro.exception.InvalidPassword;
import com.example.pro.model.Admin;
import com.example.pro.repository.AdminRepo;
import com.example.pro.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Admin registration(Admin admin) throws AdminAlreadyExist {
        Optional<Admin> existingAdmin = adminRepo.findById(admin.getEmail());
        if (existingAdmin.isPresent()) {
            throw new AdminAlreadyExist("Admin with email " + admin.getEmail() + " already exists");
        }

        Admin newAdmin = new Admin();
        newAdmin.setEmail(admin.getEmail());
        newAdmin.setName(admin.getName());
        newAdmin.setPhoneNo(admin.getPhoneNo());
        // Encrypt password before storing
        newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return adminRepo.save(newAdmin);
    }

    @Override
    public String login(String email, String password) {
        Optional<Admin> adminOptional = adminRepo.findById(email);

        if (adminOptional.isEmpty()) {
            throw new AdminDoNotExist("Admin with email " + email + " not found. Please register first.");
        }

        Admin admin = adminOptional.get();
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new InvalidPassword("Invalid password provided");
        }

        // Generate JWT token
        return jwtTokenUtil.generateToken(email);
    }
}