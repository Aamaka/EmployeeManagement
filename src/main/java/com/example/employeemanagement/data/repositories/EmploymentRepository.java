package com.example.employeemanagement.data.repositories;

import com.example.employeemanagement.data.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);
}
