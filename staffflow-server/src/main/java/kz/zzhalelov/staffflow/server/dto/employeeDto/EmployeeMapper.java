package kz.zzhalelov.staffflow.server.dto.employeeDto;

import kz.zzhalelov.staffflow.server.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee fromCreate(EmployeeCreateDto employeeCreateDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeCreateDto.getFirstName());
        employee.setLastName(employeeCreateDto.getLastName());
        employee.setIin(employeeCreateDto.getIin());
        employee.setGender(employeeCreateDto.getGender());
        employee.setEmail(employeeCreateDto.getEmail());
        employee.setPhone(employeeCreateDto.getPhone());
        employee.setAddress(employeeCreateDto.getAddress());
        employee.setCitizenship(employeeCreateDto.getCitizenship());
        return employee;
    }

    public Employee fromUpdate(EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeUpdateDto.getFirstName());
        employee.setLastName(employeeUpdateDto.getLastName());
        employee.setIin(employeeUpdateDto.getIin());
        employee.setGender(employeeUpdateDto.getGender());
        employee.setEmail(employeeUpdateDto.getEmail());
        employee.setPhone(employeeUpdateDto.getPhone());
        employee.setAddress(employeeUpdateDto.getAddress());
        employee.setCitizenship(employeeUpdateDto.getCitizenship());
        return employee;
    }

    public EmployeeResponseDto toResponse(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setIin(employee.getIin());
        dto.setGender(employee.getGender());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setCitizenship(employee.getCitizenship());
        return dto;
    }
}