package kz.zzhalelov.staffflow.server.hiring.dto;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.hiring.Hiring;
import kz.zzhalelov.staffflow.server.hiring.HiringStatus;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.position.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HiringMapper {
    public Hiring fromCreate(HiringCreateDto dto) {
        Hiring hiring = new Hiring();
        Organization organization = new Organization();
        organization.setId(dto.getOrganizationId());
        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        Position position = new Position();
        position.setId(dto.getPositionId());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        
        hiring.setHireDate(dto.getHireDate());
        hiring.setProbationPeriod(dto.getProbationPeriod());
        hiring.setStatus(HiringStatus.NOT_APPROVED);
        hiring.setComments(dto.getComments());
        return hiring;
    }

    public Hiring fromUpdate(HiringUpdateDto dto) {
        Hiring hiring = new Hiring();
        Organization organization = new Organization();
        organization.setId(dto.getOrganizationId());
        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        Position position = new Position();
        position.setId(dto.getPositionId());
        Department department = new Department();
        department.setId(dto.getDepartmentId());

        hiring.setHireDate(dto.getHireDate());
        hiring.setProbationPeriod(dto.getProbationPeriod());
        hiring.setStatus(HiringStatus.NOT_APPROVED);
        hiring.setComments(dto.getComments());
        return hiring;
    }

    public HiringResponseDto toResponse(Hiring hiring) {
        HiringResponseDto dto = new HiringResponseDto();
        dto.setId(hiring.getId());
        dto.setOrganizationId(hiring.getOrganization().getId());
        dto.setEmployeeId(hiring.getEmployee().getId());
        dto.setPositionId(hiring.getPosition().getId());
        dto.setDepartmentId(hiring.getDepartment().getId());

        dto.setHireDate(hiring.getHireDate());
        dto.setHiringStatus(hiring.getStatus());
        dto.setProbationPeriod(hiring.getProbationPeriod());
        dto.setComments(hiring.getComments());

        dto.setCreatedAt(hiring.getCreatedAt());
        dto.setUpdatedAt(hiring.getUpdatedAt());
        dto.setCreatedBy(hiring.getCreatedBy());
        dto.setUpdatedBy(hiring.getUpdatedBy());
        dto.setDeleted(hiring.isDeleted());
        dto.setDeletedAt(hiring.getDeletedAt());
        dto.setDeletedBy(hiring.getDeletedBy());
        return dto;
    }
}