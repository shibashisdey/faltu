package com.example.pro.service;

import com.example.pro.exception.AdminAlreadyExist;
import com.example.pro.model.Admin;

public interface AdminService {
    Admin registration(Admin admin) throws AdminAlreadyExist;
    String login(String email, String password);
}