package kz.zzhalelov.staffflow.gateway.controller;

import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.gateway.client.EmployeeClient;
import kz.zzhalelov.staffflow.gateway.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeClient employeeClient;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeDto dto) {
        return employeeClient.create(dto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return employeeClient.findAll();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteById(@PathVariable long employeeId) {
        return employeeClient.delete(employeeId);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> findById(@PathVariable long employeeId) {
        return employeeClient.findById(employeeId);
    }

    @GetMapping("/find-by-lastname/{lastName}")
    public ResponseEntity<Object> findByLastName(@PathVariable String lastName) {
        return employeeClient.findByLastName(lastName);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<Object> update(@PathVariable long employeeId, @RequestBody Object updatedDto) {
        return employeeClient.update(employeeId, updatedDto);
    }
}