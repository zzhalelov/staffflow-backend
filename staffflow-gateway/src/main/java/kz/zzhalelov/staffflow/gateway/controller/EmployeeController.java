package kz.zzhalelov.staffflow.gateway.controller;

import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeClient employeeClient;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeCreateDto employeeCreateDto) {
        return employeeClient.create(employeeCreateDto);
    }
}