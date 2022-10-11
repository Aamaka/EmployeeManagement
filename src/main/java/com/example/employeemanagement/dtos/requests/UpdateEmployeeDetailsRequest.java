package com.example.employeemanagement.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateEmployeeDetailsRequest {
    private String adminEmail;
    private String password;
}
