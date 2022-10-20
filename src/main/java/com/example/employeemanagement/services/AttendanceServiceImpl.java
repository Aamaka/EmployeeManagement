package com.example.employeemanagement.services;

import com.example.employeemanagement.data.model.Attendance;
import com.example.employeemanagement.data.model.Employee;
import com.example.employeemanagement.data.repositories.AttendanceRepository;
import com.example.employeemanagement.data.repositories.EmploymentRepository;
import com.example.employeemanagement.dtos.requests.SignInRequest;
import com.example.employeemanagement.dtos.requests.SignOutRequest;
import com.example.employeemanagement.dtos.response.SignInResponse;
import com.example.employeemanagement.dtos.response.SignOutResponse;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private EmploymentRepository employmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public SignInResponse signIn(SignInRequest request) {
        Optional<Employee> employee = employmentRepository.findEmployeeByEmployeeId(request.getEmployeeId());

        if (employee.isPresent()) {
            Attendance attendance = Attendance.builder()
                    .signIn(LocalDateTime.now())
                    .employee(employee.get())
                    .build();
            Attendance saved = attendanceRepository.save(attendance);
            SignInResponse response = new SignInResponse();
            response.setMessage("Have a nice day " + saved.getEmployee().getFirstName());
            return response;
        }
        throw new EmployeeNotFoundException("Invalid id");
    }

    @Override
    public SignOutResponse signOut(SignOutRequest request) {
        Employee employee = employmentRepository.findEmployeeByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Invalid id"));


        Attendance attendance = Attendance.builder()
                .signOut(LocalDateTime.now())
                .employee(employee)
                .build();

        attendanceRepository.save(attendance);
        SignOutResponse response = new SignOutResponse();
        response.setMessage("Have a great night " + employee.getFirstName());
        return response;
    }

}

