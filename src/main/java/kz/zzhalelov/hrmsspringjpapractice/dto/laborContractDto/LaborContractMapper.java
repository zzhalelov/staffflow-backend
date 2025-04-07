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

//    public LaborContract fromUpdate(LaborContractUpdateDto laborContractUpdateDto) {
//
//    }

    public LaborContractResponseDto toResponse(LaborContract laborContract) {
        Employee employee = new Employee();
        employee.setId(employee.getId());

        LaborContractResponseDto laborContractResponseDto = new LaborContractResponseDto();
        laborContractResponseDto.setEmployeeId(employee.getId());
        laborContractResponseDto.setHireDate(laborContract.getHireDate().atStartOfDay());
        laborContractResponseDto.setDepartmentId(laborContract.getDepartment().getId());
        laborContractResponseDto.setPositionId(laborContract.getPosition().getId());
        laborContractResponseDto.setLaborContractStatus(laborContract.getStatus());
        return laborContractResponseDto;
    }
}