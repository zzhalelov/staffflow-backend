package kz.zzhalelov.staffflow.server.laborContract;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.department.DepartmentRepository;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractMapper;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractResponseDto;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractUpdateDto;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.position.PositionRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebMvcTest({LaborContractController.class, LaborContractMapper.class})
class LaborContractControllerTest {
    @MockitoBean
    LaborContractService laborContractService;
    @MockitoBean
    LaborContractRepository laborContractRepository;
    @MockitoBean
    LaborContractMapper laborContractMapper;
    @MockitoBean
    EmployeeRepository employeeRepository;
    @MockitoBean
    DepartmentRepository departmentRepository;
    @MockitoBean
    PositionRepository positionRepository;
    @MockitoBean
    OrganizationRepository organizationRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        long laborContractId = 1L;
        long organizationId = 1L;
        long employeeId = 1L;
        long departmentId = 1L;
        long positionId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);

        Organization organization = new Organization();
        organization.setId(organizationId);

        Employee employee = new Employee();
        employee.setId(employeeId);

        Department department = new Department();
        department.setId(departmentId);

        Position position = new Position();
        position.setId(positionId);

        Mockito
                .when(organizationRepository.findById(organizationId))
                .thenReturn(Optional.of(organization));

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        Mockito
                .when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(department));

        Mockito
                .when(positionRepository.findById(positionId))
                .thenReturn(Optional.of(position));

        LaborContract savedContract = new LaborContract();
        savedContract.setId(laborContractId);
        savedContract.setOrganization(organization);
        savedContract.setStatus(LaborContractStatus.NOT_SIGNED);
        savedContract.setHireDate(LocalDate.of(2025, 11, 1));
        savedContract.setPosition(position);
        savedContract.setEmployee(employee);
        savedContract.setDepartment(department);

        Mockito
                .when(laborContractService.create(Mockito.any(LaborContract.class)))
                .thenReturn(savedContract);

        LaborContractResponseDto responseDto = new LaborContractResponseDto();
        responseDto.setId(laborContractId);
        responseDto.setOrganizationId(organizationId);
        responseDto.setDepartmentId(departmentId);
        responseDto.setEmployeeId(employeeId);
        responseDto.setHireDate(LocalDate.of(2025, 11, 1));
        responseDto.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        responseDto.setPositionId(positionId);

        Mockito
                .when(laborContractMapper.toResponse(Mockito.any(LaborContract.class)))
                .thenReturn(responseDto);

        String json = objectMapper.writeValueAsString(laborContract);

        mockMvc.perform(post("/api/contracts")
                        .param("organizationId", String.valueOf(organizationId))
                        .param("departmentId", String.valueOf(departmentId))
                        .param("employeeId", String.valueOf(employeeId))
                        .param("positionId", String.valueOf(positionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(laborContractId))
                .andExpect(jsonPath("organizationId").value(organizationId))
                .andExpect(jsonPath("departmentId").value(departmentId))
                .andExpect(jsonPath("employeeId").value(employeeId))
                .andExpect(jsonPath("positionId").value(positionId));
    }

    @Test
    @SneakyThrows
    void create_shouldThrow_whenOrganizationNotFound() {
        long organizationId = 1L;
        long employeeId = 1L;
        long departmentId = 1L;
        long positionId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);

        Mockito
                .when(organizationRepository.findById(organizationId))
                .thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(laborContract);

        mockMvc.perform(post("/api/contracts")
                        .param("organizationId", String.valueOf(organizationId))
                        .param("departmentId", String.valueOf(departmentId))
                        .param("employeeId", String.valueOf(employeeId))
                        .param("positionId", String.valueOf(positionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        Mockito
                .verify(laborContractService, Mockito.never())
                .create(Mockito.any(LaborContract.class));
    }

    @Test
    @SneakyThrows
    void create_shouldThrow_whenEmployeeNotFound() {
        long organizationId = 1L;
        long employeeId = 1L;
        long departmentId = 1L;
        long positionId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);

        Organization organization = new Organization();
        organization.setId(organizationId);

        Mockito
                .when(organizationRepository.findById(organizationId))
                .thenReturn(Optional.of(organization));

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(laborContract);

        mockMvc.perform(post("/api/contracts")
                        .param("organizationId", String.valueOf(organizationId))
                        .param("departmentId", String.valueOf(departmentId))
                        .param("employeeId", String.valueOf(employeeId))
                        .param("positionId", String.valueOf(positionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        Mockito
                .verify(laborContractService, Mockito.never())
                .create(Mockito.any(LaborContract.class));
    }

    @Test
    @SneakyThrows
    void create_shouldThrow_whenDepartmentNotFound() {
        long organizationId = 1L;
        long employeeId = 1L;
        long departmentId = 1L;
        long positionId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);

        Organization organization = new Organization();
        organization.setId(organizationId);

        Employee employee = new Employee();
        employee.setId(employeeId);

        Mockito
                .when(organizationRepository.findById(organizationId))
                .thenReturn(Optional.of(organization));

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        Mockito
                .when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(laborContract);

        mockMvc.perform(post("/api/contracts")
                        .param("organizationId", String.valueOf(organizationId))
                        .param("departmentId", String.valueOf(departmentId))
                        .param("employeeId", String.valueOf(employeeId))
                        .param("positionId", String.valueOf(positionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        Mockito
                .verify(laborContractService, Mockito.never())
                .create(Mockito.any(LaborContract.class));
    }

    @Test
    @SneakyThrows
    void create_shouldThrow_whenPositionNotFound() {
        long organizationId = 1L;
        long employeeId = 1L;
        long departmentId = 1L;
        long positionId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);

        Organization organization = new Organization();
        organization.setId(organizationId);

        Employee employee = new Employee();
        employee.setId(employeeId);

        Department department = new Department();
        department.setId(departmentId);

        Mockito
                .when(organizationRepository.findById(organizationId))
                .thenReturn(Optional.of(organization));

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        Mockito
                .when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(department));

        Mockito
                .when(positionRepository.findById(positionId))
                .thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(laborContract);

        mockMvc.perform(post("/api/contracts")
                        .param("organizationId", String.valueOf(organizationId))
                        .param("departmentId", String.valueOf(departmentId))
                        .param("employeeId", String.valueOf(employeeId))
                        .param("positionId", String.valueOf(positionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        Mockito
                .verify(laborContractService, Mockito.never())
                .create(Mockito.any(LaborContract.class));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        LaborContract contract1 = new LaborContract();
        contract1.setId(1L);
        contract1.setOrganization(new Organization());
        contract1.setDepartment(new Department());
        contract1.setEmployee(new Employee());
        contract1.setPosition(new Position());
        contract1.setHireDate(LocalDate.of(2025, 11, 1));
        contract1.setStatus(LaborContractStatus.NOT_SIGNED);

        LaborContract contract2 = new LaborContract();
        contract2.setId(2L);
        contract2.setOrganization(new Organization());
        contract2.setDepartment(new Department());
        contract2.setEmployee(new Employee());
        contract2.setPosition(new Position());
        contract2.setHireDate(LocalDate.of(2025, 11, 1));
        contract2.setStatus(LaborContractStatus.NOT_SIGNED);

        LaborContractResponseDto dto1 = new LaborContractResponseDto();
        dto1.setId(1L);
        dto1.setPositionId(1L);
        dto1.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        dto1.setHireDate(LocalDate.of(2025, 11, 1));
        dto1.setEmployeeId(1L);
        dto1.setDepartmentId(1L);
        dto1.setOrganizationId(1L);

        LaborContractResponseDto dto2 = new LaborContractResponseDto();
        dto2.setId(2L);
        dto2.setPositionId(2L);
        dto2.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        dto2.setHireDate(LocalDate.of(2025, 11, 1));
        dto2.setEmployeeId(2L);
        dto2.setDepartmentId(2L);
        dto2.setOrganizationId(2L);

        Mockito
                .when(laborContractService.findAll())
                .thenReturn(List.of(contract1, contract2));

        Mockito
                .when(laborContractMapper.toResponse(contract1))
                .thenReturn(dto1);

        Mockito
                .when(laborContractMapper.toResponse(contract2))
                .thenReturn(dto2);

        mockMvc.perform(get("/api/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @SneakyThrows
    void findById() {
        long contractId = 1L;
        long organizationId = 1L;
        long departmentId = 1L;
        long employeeId = 1L;
        long positionId = 1L;

        LaborContract contract = new LaborContract();
        contract.setId(1L);
        contract.setOrganization(new Organization());
        contract.setDepartment(new Department());
        contract.setEmployee(new Employee());
        contract.setPosition(new Position());
        contract.setHireDate(LocalDate.of(2025, 11, 1));
        contract.setStatus(LaborContractStatus.NOT_SIGNED);

        LaborContractResponseDto responseDto = new LaborContractResponseDto();
        responseDto.setId(contractId);
        responseDto.setPositionId(positionId);
        responseDto.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        responseDto.setHireDate(LocalDate.of(2025, 11, 1));
        responseDto.setEmployeeId(employeeId);
        responseDto.setDepartmentId(departmentId);
        responseDto.setOrganizationId(organizationId);

        Mockito
                .when(laborContractService.findById(Mockito.anyLong()))
                .thenReturn(contract);

        Mockito
                .when(laborContractMapper.toResponse(contract))
                .thenReturn(responseDto);

        mockMvc.perform(get("/api/contracts/" + contractId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId));
    }

    @Test
    @SneakyThrows
    void update_shouldReturnUpdatedContract() {
        long contractId = 1L;

        LaborContractUpdateDto dto = new LaborContractUpdateDto();
        dto.setOrganizationId(1L);
        dto.setDepartmentId(1L);
        dto.setEmployeeId(1L);
        dto.setHireDate(LocalDate.of(2025, 11, 1));
        dto.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        dto.setPositionId(1L);

        LaborContract contract = new LaborContract();
        contract.setStatus(LaborContractStatus.NOT_SIGNED);
        contract.setHireDate(LocalDate.of(2025, 11, 1));
        contract.setPosition(new Position());
        contract.setEmployee(new Employee());
        contract.setDepartment(new Department());
        contract.setOrganization(new Organization());

        LaborContract savedContract = new LaborContract();
        savedContract.setId(contractId);
        savedContract.setStatus(LaborContractStatus.NOT_SIGNED);
        savedContract.setHireDate(LocalDate.of(2025, 11, 1));
        savedContract.setPosition(new Position());
        savedContract.setEmployee(new Employee());
        savedContract.setDepartment(new Department());
        savedContract.setOrganization(new Organization());

        LaborContractResponseDto responseDto = new LaborContractResponseDto();
        responseDto.setId(contractId);
        responseDto.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        responseDto.setHireDate(LocalDate.of(2025, 11, 1));
        responseDto.setPositionId(1L);
        responseDto.setEmployeeId(1L);
        responseDto.setDepartmentId(1L);
        responseDto.setOrganizationId(1L);

        Mockito
                .when(laborContractMapper.fromUpdate(Mockito.any(LaborContractUpdateDto.class)))
                .thenReturn(contract);

        Mockito
                .when(laborContractService.update(Mockito.eq(contractId), Mockito.any(LaborContract.class)))
                .thenReturn(savedContract);

        Mockito
                .when(laborContractMapper.toResponse(Mockito.any(LaborContract.class)))
                .thenReturn(responseDto);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/contracts/" + contractId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId));
    }

    @Test
    @SneakyThrows
    void updateStatus_shouldReturnUpdatedContract() {
        long contractId = 1L;
        LaborContractStatus newStatus = LaborContractStatus.HIRED;

        LaborContract updatedContract = new LaborContract();
        updatedContract.setId(contractId);
        updatedContract.setStatus(LaborContractStatus.NOT_SIGNED);
        updatedContract.setHireDate(LocalDate.of(2025, 11, 1));
        updatedContract.setPosition(new Position());
        updatedContract.setEmployee(new Employee());
        updatedContract.setDepartment(new Department());
        updatedContract.setOrganization(new Organization());

        LaborContractResponseDto responseDto = new LaborContractResponseDto();
        responseDto.setId(contractId);
        responseDto.setLaborContractStatus(LaborContractStatus.NOT_SIGNED);
        responseDto.setHireDate(LocalDate.of(2025, 11, 1));
        responseDto.setPositionId(1L);
        responseDto.setEmployeeId(1L);
        responseDto.setDepartmentId(1L);
        responseDto.setOrganizationId(1L);

        Mockito
                .when(laborContractService.updateStatus(contractId, newStatus))
                .thenReturn(updatedContract);

        Mockito
                .when(laborContractMapper.toResponse(updatedContract))
                .thenReturn(responseDto);

        mockMvc.perform(patch("/api/contracts/" + contractId + "/status")
                        .param("status", newStatus.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId));

        Mockito
                .verify(laborContractService).updateStatus(contractId, newStatus);

        Mockito
                .verify(laborContractMapper).toResponse(updatedContract);
    }

    @Test
    @SneakyThrows
    void delete_Success() {
        long contractId = 1;

        Mockito
                .doNothing()
                .when(laborContractService)
                .delete(contractId);

        mockMvc.perform(delete("/api/contracts/" + contractId))
                .andExpect(status().isNoContent());

        Mockito
                .verify(laborContractService)
                .delete(contractId);
    }
}