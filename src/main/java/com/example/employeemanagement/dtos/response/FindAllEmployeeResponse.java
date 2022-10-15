package com.example.employeemanagement.dtos.response;

import com.example.employeemanagement.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAllEmployeeResponse {
    private  String firstName;
    private String lastName;
    private Gender gender;
    private String address;
    private String department;

}
