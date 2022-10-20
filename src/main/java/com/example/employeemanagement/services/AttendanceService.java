package com.example.employeemanagement.services;

import com.example.employeemanagement.dtos.requests.SignInRequest;
import com.example.employeemanagement.dtos.requests.SignOutRequest;
import com.example.employeemanagement.dtos.response.SignInResponse;
import com.example.employeemanagement.dtos.response.SignOutResponse;

public interface AttendanceService {

    SignInResponse signIn(SignInRequest request);
    SignOutResponse signOut(SignOutRequest request);
}
