package com.example.employeemanagement.data.model;

import com.example.employeemanagement.enums.Gender;
import com.example.employeemanagement.enums.Role;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;


}
