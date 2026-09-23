package com.example.employee_api;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void getEmployeeById_shouldReturnEmployee() {

        Employee employee =
                new Employee(3, "Raj", 90000, "IT");

        when(repository.findById(3))
                .thenReturn(Optional.of(employee));

        Employee result = service.getEmployeeById(3);

        assertEquals("Raj", result.getName());
        assertEquals(90000, result.getSalary());
        assertEquals("IT", result.getDepartment());
    }
    @Test
void getEmployeeById_shouldThrowException_whenEmployeeNotFound() {

    when(repository.findById(999))
            .thenReturn(Optional.empty());

    EmployeeNotFoundException exception =
            assertThrows(
                    EmployeeNotFoundException.class,
                    () -> service.getEmployeeById(999)
            );

    assertEquals(
            "Employee not found with id: 999",
            exception.getMessage()
    );
    
}



@Test
void addEmployee_shouldReturnSavedEmployee() {

    Employee employee =
            new Employee(8, "Amit", 60000, "HR");

    when(repository.save(employee))
            .thenReturn(employee);

    Employee result = service.addEmployee(employee);

    assertEquals(8, result.getId());
    assertEquals("Amit", result.getName());
    assertEquals(60000, result.getSalary());
    assertEquals("HR", result.getDepartment());
}

@Test
void updateEmployee_shouldUpdateEmployee() {

    Employee existingEmployee =
            new Employee(3, "Raj", 90000, "IT");

    Employee updatedEmployee =
            new Employee(3, "Raj", 95000, "IT");

    when(repository.findById(3))
            .thenReturn(Optional.of(existingEmployee));

    when(repository.save(existingEmployee))
            .thenReturn(existingEmployee);

    Employee result =
            service.updateEmployee(3, updatedEmployee);

    assertEquals(3, result.getId());
    assertEquals("Raj", result.getName());
    assertEquals(95000, result.getSalary());
    assertEquals("IT", result.getDepartment());
}

@Test
void deleteEmployee_shouldCallRepository() {

    service.deleteEmployee(5);

    verify(repository).deleteById(5);
}
}