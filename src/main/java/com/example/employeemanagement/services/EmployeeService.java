package com.example.employeemanagement.services;

import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.requests.UpdateEmployeeDetailsRequest;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.dtos.response.UpdateEmployeeDetailsResponse;
import com.github.fge.jsonpatch.JsonPatch;

public interface EmployeeService {
    RegisterEmployeeResponse register (RegisterEmployeeRequest request);

    UpdateEmployeeDetailsResponse update(long id, UpdateEmployeeDetailsRequest request, JsonPatch patch);

}
