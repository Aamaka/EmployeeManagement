package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/employee/")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("register/")
    public RegisterEmployeeResponse register(@RequestBody RegisterEmployeeRequest request){
        return employeeService.register(request);
    }
}
