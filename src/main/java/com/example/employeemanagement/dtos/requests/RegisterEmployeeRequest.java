package com.example.employeemanagement.dtos.requests;

import com.example.employeemanagement.enums.Department;
import com.example.employeemanagement.enums.Gender;
import com.example.employeemanagement.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterEmployeeRequest {


    private String firstName;
    private String lastName;
    private String address;

    private String email;
    private String adminEmail;
    private String adminPassword;

    private String phoneNumber;

    private Gender gender;

    private Role role;

    private Department department;

}
