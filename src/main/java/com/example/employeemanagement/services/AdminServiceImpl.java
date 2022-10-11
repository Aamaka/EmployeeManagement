package com.example.employeemanagement.services;

import com.example.employeemanagement.configuration.SecuredAdmin;
import com.example.employeemanagement.data.model.Admin;
import com.example.employeemanagement.data.repositories.AdminRepository;
import com.example.employeemanagement.dtos.requests.AdminRegistrationRequest;
import com.example.employeemanagement.dtos.requests.LoginAdminRequest;
import com.example.employeemanagement.dtos.response.AdminRegistrationResponse;
import com.example.employeemanagement.dtos.response.LoginAdminResponse;
import com.example.employeemanagement.exceptions.EmailAlreadyExistException;
import com.example.employeemanagement.exceptions.EmailDoesNotExistException;
import com.example.employeemanagement.exceptions.PasswordMismatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminRegistrationResponse register(AdminRegistrationRequest request) {
        if(adminRepository.existsByEmail(request.getEmail())) throw new EmailAlreadyExistException("you already have " +
                "an account with this email");
        Admin admin = modelMapper.map(request, Admin.class);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        if(request.getPassword().equals(request.getConfirmPassword())){
            Admin saved = adminRepository.save(admin);
            return AdminRegistrationResponse.builder()
                    .message("Registration was successful, welcome "+ saved.getName())
                    .build();
        }
        throw new PasswordMismatchException("password does not match");
    }

    @Override
    public LoginAdminResponse login(LoginAdminRequest request) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(request.getEmail());
        if(admin.isPresent()){
            if(passwordEncoder.matches(request.getPassword(), admin.get().getPassword())){
                return LoginAdminResponse.builder()
                        .message("Welcome back "+admin.get().getName())
                        .build();
            }
            throw new PasswordMismatchException("Invalid password");
        }
        throw new EmailDoesNotExistException("Invalid password");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("admin name not found"));
        return new SecuredAdmin(admin);
    }
}
