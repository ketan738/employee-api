package com.example.employee_api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Employee> searchEmployeesByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Employee getEmployeeById(int id) {

        logger.info("Fetching employee with ID: {}", id);

        Employee employee = repository.findById(id)
                .orElse(null);

        if (employee == null) {

            logger.warn("Employee not found with ID: {}", id);

            throw new EmployeeNotFoundException(
                    "Employee not found with id: " + id);
        }

        return employee;
    }

    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Page<Employee> getEmployeesByDepartment(
            String department, Pageable pageable) {

        return repository.findByDepartment(department, pageable);
    }

    public List<Employee> getEmployeesByMinimumSalary(double salary) {
        return repository.findBySalaryGreaterThanEqual(salary);
    }

    public List<Employee> getEmployeesByDepartmentAndSalary(
            String department, double salary) {

        return repository.findEmployeesByDepartmentAndSalary(
                department, salary);
    }

    public Employee updateEmployee(int id, Employee employee) {

        Employee existingEmployee =
                repository.findById(id).orElse(null);

        if (existingEmployee == null) {

            logger.warn("Cannot update. Employee not found with ID: {}", id);

            return null;
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setDepartment(employee.getDepartment());

        return repository.save(existingEmployee);
    }

   public void deleteEmployee(int id) {

    logger.info("Deleting employee with ID: {}", id);

    repository.deleteById(id);
}

    public EmployeeDTO convertToDTO(Employee employee) {

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getDepartment()
        );
    }
}