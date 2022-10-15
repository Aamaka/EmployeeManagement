package com.example.employeemanagement.services;

import com.example.employeemanagement.data.model.Employee;
import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.requests.UpdateEmployeeDetailsRequest;
import com.example.employeemanagement.dtos.response.FindAllEmployeeResponse;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.dtos.response.UpdateEmployeeDetailsResponse;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;

public interface EmployeeService {
    RegisterEmployeeResponse register (RegisterEmployeeRequest request);

    UpdateEmployeeDetailsResponse update(long id, JsonPatch patch) throws JsonPatchException;

    List<FindAllEmployeeResponse> findAllEmployees();

}
