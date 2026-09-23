package com.example.employee_api;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
public Page<Employee> getAllEmployees(Pageable pageable) {
    return service.getAllEmployees(pageable);
}

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return service.getEmployeeById(id);


    }

    @GetMapping("/employees/search-name")
    public List<Employee> searchEmployeesByName(
        @RequestParam String name) {

    return service.searchEmployeesByName(name);
    }

    @GetMapping("/employees/dto/{id}")
    public EmployeeDTO getEmployeeDTO(@PathVariable int id) {

    Employee employee = service.getEmployeeById(id);

    return service.convertToDTO(employee);
}

    @PostMapping("/employees")
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
    return service.addEmployee(employee);
    }

    @PutMapping("/employees/{id}")
         public Employee updateEmployee(
        @PathVariable int id,
        @RequestBody Employee employee) {

    return service.updateEmployee(id, employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable int id) {
    service.deleteEmployee(id);
    }

    @GetMapping("/employees/search")
    public Page<Employee> getEmployeesByDepartment(
        @RequestParam String department,
        Pageable pageable) {

    return service.getEmployeesByDepartment(
            department, pageable);
}
     @GetMapping("/employees/salary")
    public List<Employee> getEmployeesByMinimumSalary(
        @RequestParam double minSalary) {

       return service.getEmployeesByMinimumSalary(minSalary);
    }

    @GetMapping("/employees/filter")
    public List<Employee> getEmployeesByDepartmentAndSalary(
        @RequestParam String department,
        @RequestParam double salary) {

    return service.getEmployeesByDepartmentAndSalary(department, salary);
}

    
}