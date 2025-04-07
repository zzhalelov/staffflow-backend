package kz.zzhalelov.hrmsspringjpapractice.dto.departmentDto;

import kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto.EmployeeMapper;
import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    private final EmployeeMapper employeeMapper;

    public Department fromCreate(DepartmentCreateDto departmentCreateDto) {
        Employee employee = new Employee();
        employee.setId(departmentCreateDto.getManagerId());
        Department department = new Department();
        department.setName(departmentCreateDto.getName());
        department.setManager(employee);
        return department;
    }

    public Department fromUpdate(DepartmentUpdateDto departmentUpdateDto) {
        Employee employee = new Employee();
        employee.setId(departmentUpdateDto.getManagerId());
        Department department = new Department();
        department.setName(departmentUpdateDto.getName());
        department.setManager(employee);
        return department;
    }

    public DepartmentResponseDto toResponse(Department department) {
        Employee employee = new Employee();
        employee.setId(employee.getId());

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setName(department.getName());
        departmentResponseDto.setManagerId(employee.getId());
        return departmentResponseDto;
    }
}