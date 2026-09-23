package com.example.employee_api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Page<Employee> findByDepartment(String department, Pageable pageable);
    List<Employee> findBySalaryGreaterThanEqual(double salary);
    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.salary >= :salary")
    List<Employee> findEmployeesByDepartmentAndSalary(
        String department,
        double salary);
}