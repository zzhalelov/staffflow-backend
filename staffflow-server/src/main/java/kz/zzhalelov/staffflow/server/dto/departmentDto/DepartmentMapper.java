package kz.zzhalelov.staffflow.server.dto.departmentDto;

import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeMapper;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.model.Department;
import kz.zzhalelov.staffflow.server.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    private final EmployeeMapper employeeMapper;

    public Department fromCreate(DepartmentCreateDto departmentCreateDto) {
        Department department = new Department();
        department.setName(departmentCreateDto.getName());
        Employee manager = new Employee();
        manager.setId(departmentCreateDto.getManagerId());
        department.setManager(manager);

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
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setName(department.getName());
        Employee manager = department.getManager();
        EmployeeShortResponseDto managerDto = new EmployeeShortResponseDto();
        managerDto.setId(manager.getId());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setLastName(manager.getLastName());
        dto.setManager(managerDto);
        return dto;
    }
}