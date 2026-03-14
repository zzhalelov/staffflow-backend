package kz.zzhalelov.staffflow.server.laborContract.dto;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentShortResponseDto;
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
        laborContract.setHireDate(dto.getHireDate());
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

        OrganizationShortResponseDto organizationShortDto = new OrganizationShortResponseDto();
        organizationShortDto.setId(laborContract.getOrganization().getId());
        organizationShortDto.setName(laborContract.getOrganization().getShortName());

        EmployeeShortResponseDto employeeShortDto = new EmployeeShortResponseDto();
        employeeShortDto.setId(laborContract.getEmployee().getId());
        employeeShortDto.setFirstName(laborContract.getEmployee().getFirstName());
        employeeShortDto.setLastName(laborContract.getEmployee().getLastName());

        DepartmentShortResponseDto departmentShortDto = new DepartmentShortResponseDto();
        departmentShortDto.setId(laborContract.getDepartment().getId());
        departmentShortDto.setName(laborContract.getDepartment().getName());

        PositionResponseDto positionDto = new PositionResponseDto();
        positionDto.setId(laborContract.getPosition().getId());
        positionDto.setName(laborContract.getPosition().getName());

        dto.setOrganization(organizationShortDto);
        dto.setPosition(positionDto);
        dto.setDepartment(departmentShortDto);
        dto.setEmployee(employeeShortDto);
        dto.setLaborContractStatus(laborContract.getStatus());
        dto.setHireDate(laborContract.getHireDate());
        return dto;
    }
}