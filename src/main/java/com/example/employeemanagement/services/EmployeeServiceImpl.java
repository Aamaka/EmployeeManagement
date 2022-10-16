package com.example.employeemanagement.services;

import com.example.employeemanagement.data.model.Admin;
import com.example.employeemanagement.data.model.Employee;
import com.example.employeemanagement.data.repositories.AdminRepository;
import com.example.employeemanagement.data.repositories.EmploymentRepository;
import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.response.FindAllEmployeeResponse;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.dtos.response.UpdateEmployeeDetailsResponse;
import com.example.employeemanagement.enums.Department;
import com.example.employeemanagement.enums.Role;
import com.example.employeemanagement.exceptions.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.employeemanagement.enums.Department.*;
import static com.example.employeemanagement.validation.ValidateEmail.validateEmail;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
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
                    if(validateEmail(request.getEmail())){
                        if(!employmentRepository.existsByEmail(request.getEmail())) {
                            Employee employee = modelMapper.map(request, Employee.class);
                            String employeeId = String.valueOf(UUID.randomUUID().getMostSignificantBits());
                            switch (employee.getDepartment()){
                                case IT -> employeeId = employeeId.substring(1, 5)+"IT";
                                case LOGISTIC -> employeeId = employeeId.substring(1, 5) + "LT";
                                case PROCUREMENT -> employeeId = employeeId.substring(1, 5) + "PT";
                            }

                            employee.setEmployeeId(employeeId);

                            return getRegisterEmployeeResponse(employee);
                        }
                        throw new EmailAlreadyExistException("Employee already exist, make an update instead");
                    }
                    throw new AccessException("Access not granted");
                }
                throw new PasswordMismatchException("Incorrect password");
            }
            throw new EmailDoesNotExistException("Admin email does not exist");
        }
        throw new InvalidSyntaxException("Invalid email syntax");

    }

    private RegisterEmployeeResponse getRegisterEmployeeResponse(Employee employee) {
        Employee employed = employmentRepository.save(employee);
        return RegisterEmployeeResponse.builder()
                .message(String.format("Successfully onboarded %s %s to the platform",
                        employed.getFirstName(), employed.getLastName()))
                .id("Your id is: "+employed.getEmployeeId())
                .build();
    }

    @Override
    public UpdateEmployeeDetailsResponse update(long id, JsonPatch patch) throws JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = employmentRepository.findById(id).orElseThrow(()->
                new EmployeeNotFoundException("employee not found"));
        JsonNode emp = objectMapper.convertValue(employee, JsonNode.class);
        try {
            JsonNode updatedNode = patch.apply(emp);
            Employee employee1 = objectMapper.convertValue(updatedNode, Employee.class);
            log.info("{}", employee1);
            employmentRepository.save(employee1);
            return UpdateEmployeeDetailsResponse.builder()
                    .message("successful")
                    .build();
        } catch (JsonPatchException exception) {
            throw exception;
        }

    }

    @Override
    public List<FindAllEmployeeResponse> findAllEmployees() {
        List<FindAllEmployeeResponse> employeeResponses = new ArrayList<>();
        List<Employee> found =  employmentRepository.findAll();
        log.info(" Employees===> {}, {}", found, found.size());
        found.forEach(employee -> buildFindAllEmployeesResponse(employee, employeeResponses));
        return employeeResponses;
    }

    @Override
    public Employee findAnEmployee(String id) {
        return employmentRepository.findEmployeeByEmployeeId(id).orElseThrow(()->
              new EmployeeNotFoundException("Employee not found"));

    }

    private void buildFindAllEmployeesResponse(Employee employee, List<FindAllEmployeeResponse> employeeResponses){
        FindAllEmployeeResponse f = new FindAllEmployeeResponse();
        modelMapper.map(employee, f);
        employeeResponses.add(f);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employmentRepository.findEmployeeByEmail(username);
        return null;
    }
}
