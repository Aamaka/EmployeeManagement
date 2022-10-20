package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.requests.SignInRequest;
import com.example.employeemanagement.dtos.requests.SignOutRequest;
import com.example.employeemanagement.dtos.response.SignInResponse;
import com.example.employeemanagement.dtos.response.SignOutResponse;
import com.example.employeemanagement.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/signIn")
    public SignInResponse signIn(@RequestBody SignInRequest request){
        return attendanceService.signIn(request);
    }

    @PostMapping("/signOut")
    public SignOutResponse signOut(@RequestBody SignOutRequest request){
        return attendanceService.signOut(request);
    }
}
