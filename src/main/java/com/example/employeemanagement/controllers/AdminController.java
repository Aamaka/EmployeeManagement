package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.requests.AdminRegistrationRequest;
import com.example.employeemanagement.dtos.requests.LoginAdminRequest;
import com.example.employeemanagement.dtos.response.AdminRegistrationResponse;
import com.example.employeemanagement.dtos.response.LoginAdminResponse;
import com.example.employeemanagement.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/admin/")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("register/")
    public AdminRegistrationResponse register(@RequestBody AdminRegistrationRequest request){
        return adminService.register(request);
    }

    @PatchMapping("login/")
    public LoginAdminResponse login(@RequestBody LoginAdminRequest request){
        return adminService.login(request);
    }
}
