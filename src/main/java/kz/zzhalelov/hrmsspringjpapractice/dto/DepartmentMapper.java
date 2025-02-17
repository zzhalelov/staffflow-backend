package kz.zzhalelov.hrmsspringjpapractice.dto;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {
    public DepartmentDto toDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setManager(department.getManager().getId());
        return departmentDto;
    }

    public List<DepartmentDto> toDto(List<Department> departments) {
        return departments.stream()
                .map(this::toDto)
                .toList();
    }
}