package kz.zzhalelov.staffflow.gateway.controller;

import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeClient employeeClient;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeCreateDto employeeCreateDto) {
        return employeeClient.create(employeeCreateDto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return employeeClient.findAll();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteById(@PathVariable long employeeId) {
        return employeeClient.delete(employeeId);
    }
}