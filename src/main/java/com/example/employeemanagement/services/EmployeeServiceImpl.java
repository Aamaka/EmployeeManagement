package com.example.employeemanagement.services;

import com.example.employeemanagement.data.model.Admin;
import com.example.employeemanagement.data.model.Employee;
import com.example.employeemanagement.data.repositories.AdminRepository;
import com.example.employeemanagement.data.repositories.EmploymentRepository;
import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.requests.UpdateEmployeeDetailsRequest;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.dtos.response.UpdateEmployeeDetailsResponse;
import com.example.employeemanagement.enums.Role;
import com.example.employeemanagement.exceptions.*;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmploymentRepository employmentRepository;
    private final AdminRepository adminRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public RegisterEmployeeResponse register(RegisterEmployeeRequest request) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(request.getAdminEmail());
        return getRegisterEmployeeResponse(request, admin);
    }

    private RegisterEmployeeResponse getRegisterEmployeeResponse(RegisterEmployeeRequest request, Optional<Admin> admin) {
        if(admin.isPresent()){
            if(passwordEncoder.matches(request.getAdminPassword(), admin.get().getPassword())){
                if(admin.get().getRole().equals(Role.ADMIN)){
                    if(!employmentRepository.existsByEmail(request.getEmail())) {

                        Employee employee = modelMapper.map(request, Employee.class);

                        Employee employed = employmentRepository.save(employee);
                        return RegisterEmployeeResponse.builder()
                                .message(String.format("Successfully onboarded %s %s to the platform",
                                        employed.getFirstName(), employed.getLastName()))
                                .build();
                    }
                    throw new EmailAlreadyExistException("Employee already exist, make an update instead");
                }
                throw new AccessException("Access not granted");
            }
            throw new PasswordMismatchException("Incorrect password");
        }
        throw new EmailDoesNotExistException("Admin email does not exist");
    }

    @Override
    public UpdateEmployeeDetailsResponse update(long id, UpdateEmployeeDetailsRequest request, JsonPatch patch) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(request.getAdminEmail());
        if(admin.isPresent()){
            if(passwordEncoder.matches(request.getPassword(), admin.get().getPassword())){
                Employee employee = employmentRepository.findById(id)
                        .orElseThrow(()->new EmployeeNotFoundException(""));


            }
        }
        return null;
    }


}
