package kz.zzhalelov.staffflow.gateway.controller;

import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.gateway.client.DepartmentClient;
import kz.zzhalelov.staffflow.gateway.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentClient departmentClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody DepartmentDto dto) {
        return departmentClient.create(dto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return departmentClient.findAll();
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Object> findById(@PathVariable long departmentId) {
        return departmentClient.findById(departmentId);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Object> deleteById(@PathVariable long departmentId) {
        return departmentClient.delete(departmentId);
    }
}