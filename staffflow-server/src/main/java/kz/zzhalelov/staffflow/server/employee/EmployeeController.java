package kz.zzhalelov.staffflow.server.employee;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeCreateDto;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeMapper;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeResponseDto;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    //find all employees
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> findAll(@RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        List<Employee> employeeList = employeeService.findAll(from, size);
        return employeeList.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    //find by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDto findById(@PathVariable long id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    //create
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDto create(@RequestBody EmployeeCreateDto employeeCreateDto) {
        Employee employee = employeeMapper.fromCreate(employeeCreateDto);
        return employeeMapper.toResponse(employeeService.create(employee));
    }

    //update
    @PatchMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDto update(@PathVariable long employeeId,
                                      @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = employeeMapper.fromUpdate(employeeUpdateDto);
        return employeeMapper.toResponse(employeeService.update(employeeId, employee));
    }

    //delete
    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable long employeeId) {
        employeeService.delete(employeeId);
    }

    //find by last name
    @GetMapping("/find-by-lastname/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDto> findByLastName(@PathVariable String lastName) {
        return employeeService.findByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}