package kz.zzhalelov.hrmsspringjpapractice.dto;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department fromCreate(DepartmentCreateDto departmentCreateDto) {
        Department department = new Department();
        department.setName(departmentCreateDto.getName());
        department.setManager(departmentCreateDto.getManagerId());
        return department;
    }

    public Department fromUpdate(DepartmentUpdateDto departmentUpdateDto) {
        Department department = new Department();
        department.setName(departmentUpdateDto.getName());
        department.setManager(departmentUpdateDto.getManager());
        return department;
    }

    public DepartmentResponseDto toResponse(Department department) {
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setName(department.getName());
        departmentResponseDto.setManager(department.getManager().getId());
        return departmentResponseDto;
    }
}