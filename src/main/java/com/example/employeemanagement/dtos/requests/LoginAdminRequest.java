package com.example.employeemanagement.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginAdminRequest {
    private String email;
    private String password;
}
