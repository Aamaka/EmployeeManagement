package com.example.employeemanagement.services;

import com.example.employeemanagement.dtos.requests.AdminRegistrationRequest;
import com.example.employeemanagement.dtos.requests.LoginAdminRequest;
import com.example.employeemanagement.dtos.response.AdminRegistrationResponse;
import com.example.employeemanagement.dtos.response.LoginAdminResponse;
import com.example.employeemanagement.enums.Gender;
import com.example.employeemanagement.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void register() {
        AdminRegistrationRequest admin = AdminRegistrationRequest.builder()
                .address("sabo")
                .email("lekan2@gmail.com")
                .password("7778")
                .confirmPassword("7778")
                .gender(Gender.MALE)
                .phoneNumber("09087653456")
                .name("Lekan")
                .role(Role.ADMIN)
                .build();

        AdminRegistrationResponse response = adminService.register(admin);

        assertThat(response).isNotNull();
        assertThat("Registration was successful, welcome Lekan").isEqualTo(response.getMessage());
    }

    @Test
    public void login() {
        LoginAdminRequest request = LoginAdminRequest.builder()
                .email("lekan2@gmail.com")
                .password("7778")
                .build();
        LoginAdminResponse response = adminService.login(request);
        assertThat("Welcome back Lekan").isEqualTo(response.getMessage());
    }
}