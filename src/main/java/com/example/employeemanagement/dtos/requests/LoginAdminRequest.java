package com.example.employeemanagement.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginAdminRequest {
    private String email;
    private String password;
}
