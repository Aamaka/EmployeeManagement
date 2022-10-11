package com.example.employeemanagement.services;

import com.example.employeemanagement.dtos.requests.AdminRegistrationRequest;
import com.example.employeemanagement.dtos.requests.LoginAdminRequest;
import com.example.employeemanagement.dtos.response.AdminRegistrationResponse;
import com.example.employeemanagement.dtos.response.LoginAdminResponse;

public interface AdminService {
    AdminRegistrationResponse register (AdminRegistrationRequest request);
    LoginAdminResponse login (LoginAdminRequest request);
}
