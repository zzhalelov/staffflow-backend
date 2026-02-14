package kz.zzhalelov.staffflow.server.laborContract.dto;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.laborContract.LaborContract;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.position.dto.PositionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LaborContractMapper {

    public LaborContract fromCreate(LaborContractCreateDto dto) {
        LaborContract laborContract = new LaborContract();
        Organization organization = new Organization();
        organization.setId(dto.getOrganizationId());
        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        Position position = new Position();
        position.setId(dto.getPositionId());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        laborContract.setOrganization(organization);
        laborContract.setEmployee(employee);
        laborContract.setHireDate(dto.getHireDate());
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);
        return laborContract;
    }

    public LaborContract fromUpdate(LaborContractUpdateDto dto) {
        Organization organization = new Organization();
        organization.setId(dto.getOrganizationId());
        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        Position position = new Position();
        position.setId(dto.getPositionId());

        LaborContract laborContract = new LaborContract();
        laborContract.setOrganization(organization);
        laborContract.setEmployee(employee);
        laborContract.setHireDate(dto.getHireDate());
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        laborContract.setStatus(dto.getLaborContractStatus());
        return laborContract;
    }

    public LaborContractResponseDto toResponse(LaborContract laborContract) {
        LaborContractResponseDto dto = new LaborContractResponseDto();
        dto.setId(laborContract.getId());
        dto.setOrganization(new OrganizationShortResponseDto());
        dto.setEmployee(new EmployeeShortResponseDto());
        dto.setHireDate(laborContract.getHireDate());
        dto.setDepartment(laborContract.getDepartment());

        PositionResponseDto positionDto = new PositionResponseDto();
        positionDto.setId(laborContract.getPosition().getId());
        positionDto.setName(laborContract.getPosition().getName());
        dto.setPosition(positionDto);

        dto.setLaborContractStatus(laborContract.getStatus());
        return dto;
    }
}