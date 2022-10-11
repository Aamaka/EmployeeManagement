package com.example.employeemanagement.dtos.requests;

import com.example.employeemanagement.enums.Gender;
import com.example.employeemanagement.enums.Role;
import lombok.*;
import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminRegistrationRequest {
    private String name;
    private String address;

    @Email
    private String email;

    private String phoneNumber;

    private Gender gender;

    private Role role;

    private String password;
    private String confirmPassword;
}
