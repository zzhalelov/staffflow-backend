package kz.zzhalelov.hrmsspringjpapractice.dto.laborContractDto;

import kz.zzhalelov.hrmsspringjpapractice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LaborContractMapper {

    public LaborContract fromCreate(LaborContractCreateDto laborContractCreateDto) {
        LaborContract laborContract = new LaborContract();
        Employee employee = new Employee();
        employee.setId(laborContractCreateDto.getEmployeeId());
        Position position = new Position();
        position.setId(laborContractCreateDto.getPositionId());
        Department department = new Department();
        department.setId(laborContractCreateDto.getDepartmentId());
        laborContract.setEmployee(employee);
        laborContract.setHireDate(laborContractCreateDto.getHireDate().toLocalDate());
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);
        return laborContract;
    }

    public LaborContract fromUpdate(LaborContractUpdateDto laborContractUpdateDto) {
        Employee employee = new Employee();
        employee.setId(laborContractUpdateDto.getEmployeeId());
        Department department = new Department();
        department.setId(laborContractUpdateDto.getDepartmentId());
        Position position = new Position();
        position.setId(laborContractUpdateDto.getPositionId());

        LaborContract laborContract = new LaborContract();
        laborContract.setEmployee(employee);
        laborContract.setHireDate(laborContractUpdateDto.getHireDate().toLocalDate());
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        laborContract.setStatus(laborContractUpdateDto.getLaborContractStatus());
        return laborContract;
    }

    public LaborContractResponseDto toResponse(LaborContract laborContract) {
        LaborContractResponseDto laborContractResponseDto = new LaborContractResponseDto();
        laborContractResponseDto.setEmployeeId(laborContract.getEmployee().getId());
        laborContractResponseDto.setHireDate(laborContract.getHireDate().atStartOfDay());
        laborContractResponseDto.setDepartmentId(laborContract.getDepartment().getId());
        laborContractResponseDto.setPositionId(laborContract.getPosition().getId());
        laborContractResponseDto.setLaborContractStatus(laborContract.getStatus());
        return laborContractResponseDto;
    }
}