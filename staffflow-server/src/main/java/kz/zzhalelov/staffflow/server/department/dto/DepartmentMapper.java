package kz.zzhalelov.staffflow.server.department.dto;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeMapper;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
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