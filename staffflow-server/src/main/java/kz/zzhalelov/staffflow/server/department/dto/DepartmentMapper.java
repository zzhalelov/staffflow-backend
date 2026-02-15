package kz.zzhalelov.staffflow.server.department.dto;

import kz.zzhalelov.staffflow.server.department.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department fromCreate(DepartmentCreateDto departmentCreateDto) {
        Department department = new Department();
        department.setName(departmentCreateDto.getName());
        return department;
    }

    public Department fromUpdate(DepartmentUpdateDto departmentUpdateDto) {
        Department department = new Department();
        department.setName(departmentUpdateDto.getName());
        return department;
    }

    public DepartmentResponseDto toResponse(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());
        return dto;
    }
}