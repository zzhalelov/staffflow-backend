package kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto;

import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
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
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setFirstName(employee.getFirstName());
        employeeResponseDto.setLastName(employee.getLastName());
        employeeResponseDto.setIin(employee.getIin());
        employeeResponseDto.setGender(employee.getGender());
        employeeResponseDto.setEmail(employee.getEmail());
        employeeResponseDto.setPhone(employee.getPhone());
        employeeResponseDto.setAddress(employee.getAddress());
        employeeResponseDto.setCitizenship(employee.getCitizenship());
        return employeeResponseDto;
    }
}