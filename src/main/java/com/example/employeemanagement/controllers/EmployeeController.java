package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.requests.RegisterEmployeeRequest;
import com.example.employeemanagement.dtos.response.FindAllEmployeeResponse;
import com.example.employeemanagement.dtos.response.RegisterEmployeeResponse;
import com.example.employeemanagement.dtos.response.UpdateEmployeeDetailsResponse;
import com.example.employeemanagement.services.EmployeeService;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("register/")
    public RegisterEmployeeResponse register(@RequestBody RegisterEmployeeRequest request){
        return employeeService.register(request);
    }

    @GetMapping("/findAll")
    public List<FindAllEmployeeResponse> findAllEmployees(){
        return employeeService.findAllEmployees();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable Long id, @RequestBody JsonPatch patch){

        log.info("here-->{}", patch.toString());
        try {
            UpdateEmployeeDetailsResponse res = employeeService.update(id, patch);
            return ResponseEntity.ok(res);
        } catch (JsonPatchException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
