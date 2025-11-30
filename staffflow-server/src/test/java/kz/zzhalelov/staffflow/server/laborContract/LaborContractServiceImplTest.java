package kz.zzhalelov.staffflow.server.laborContract;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LaborContractServiceImplTest {
    @Mock
    private LaborContractRepository laborContractRepository;
    @Mock
    private LaborContractHistoryRepository historyRepository;
    @InjectMocks
    private LaborContractServiceImpl laborContractService;

    @Test
    void create_shouldCreate() {
        LaborContract laborContract = new LaborContract();
        laborContract.setId(1L);
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setDepartment(new Department());
        laborContract.setEmployee(new Employee());
        laborContract.setOrganization(new Organization());
        laborContract.setPosition(new Position());

        Mockito
                .when(laborContractRepository.save(any(LaborContract.class)))
                .thenReturn(laborContract);

        LaborContract savedContract = laborContractService.create(laborContract);

        assertEquals(laborContract.getId(), savedContract.getId());
        assertEquals(laborContract.getDepartment(), savedContract.getDepartment());
        assertEquals(laborContract.getEmployee(), savedContract.getEmployee());
        assertEquals(laborContract.getHireDate(), savedContract.getHireDate());
        assertEquals(laborContract.getOrganization(), savedContract.getOrganization());
        assertEquals(laborContract.getPosition(), savedContract.getPosition());
        assertEquals(laborContract.getStatus(), savedContract.getStatus());
    }

    @Test
    void findAll_shouldReturnList() {
        LaborContract laborContract1 = new LaborContract();
        laborContract1.setId(1L);
        laborContract1.setStatus(LaborContractStatus.NOT_SIGNED);
        laborContract1.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract1.setDepartment(new Department());
        laborContract1.setEmployee(new Employee());
        laborContract1.setOrganization(new Organization());
        laborContract1.setPosition(new Position());

        LaborContract laborContract2 = new LaborContract();
        laborContract2.setId(2L);
        laborContract2.setStatus(LaborContractStatus.NOT_SIGNED);
        laborContract2.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract2.setDepartment(new Department());
        laborContract2.setEmployee(new Employee());
        laborContract2.setOrganization(new Organization());
        laborContract2.setPosition(new Position());

        List<LaborContract> contracts = List.of(laborContract1, laborContract2);

        Mockito
                .when(laborContractRepository.findAll())
                .thenReturn(contracts);
        List<LaborContract> savedContracts = laborContractService.findAll();
        assertEquals(2, savedContracts.size());
        assertEquals(laborContract1.getId(), savedContracts.get(0).getId());
        assertEquals(laborContract2.getId(), savedContracts.get(1).getId());
    }

    @Test
    void findById() {
        LaborContract laborContract = new LaborContract();
        laborContract.setId(1L);
        laborContract.setStatus(LaborContractStatus.NOT_SIGNED);
        laborContract.setHireDate(LocalDate.of(2025, 11, 1));
        laborContract.setDepartment(new Department());
        laborContract.setEmployee(new Employee());
        laborContract.setOrganization(new Organization());
        laborContract.setPosition(new Position());

        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.of(laborContract));

        LaborContract savedContract = laborContractService.findById(1L);
        assertEquals(laborContract.getId(), savedContract.getId());
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        long contractId = 1;

        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> laborContractService.findById(1L)
        );

        assertEquals("Contract with id " + contractId + " not found!", ex.getMessage());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.of(new LaborContract()));

        laborContractService.delete(1L);

        Mockito
                .verify(laborContractRepository)
                .deleteById(1L);
    }

    @Test
    void delete_shouldThrow_whenStatusIsHired() {
        long contractId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setId(contractId);
        laborContract.setStatus(LaborContractStatus.HIRED);

        Mockito
                .when(laborContractRepository.findById(contractId))
                .thenReturn(Optional.of(laborContract));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> laborContractService.delete(contractId)
        );

        assertEquals("Contract with id " + contractId + " is already hired!", ex.getMessage());
        Mockito
                .verify(laborContractRepository, Mockito.never())
                .deleteById(Mockito.anyLong());
    }

    @Test
    void delete_shouldThrow_whenStatusIsResigned() {
        long contractId = 1L;

        LaborContract laborContract = new LaborContract();
        laborContract.setId(contractId);
        laborContract.setStatus(LaborContractStatus.RESIGNED);

        Mockito
                .when(laborContractRepository.findById(contractId))
                .thenReturn(Optional.of(laborContract));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> laborContractService.delete(contractId)
        );

        assertEquals("Contract with id " + contractId + " is resigned!", ex.getMessage());
        Mockito
                .verify(laborContractRepository, Mockito.never())
                .deleteById(Mockito.anyLong());
    }

    @Test
    void updateStatus_shouldUpdateStatus() {
        LaborContract existingContract = new LaborContract();
        existingContract.setId(1L);
        existingContract.setStatus(LaborContractStatus.NOT_SIGNED);

        LaborContract updatedContract = new LaborContract();
        updatedContract.setStatus(LaborContractStatus.HIRED);

        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.of(existingContract));

        laborContractService.updateStatus(1L, LaborContractStatus.HIRED);

        assertEquals(1L, existingContract.getId());
        assertEquals(LaborContractStatus.HIRED, existingContract.getStatus());
    }

    @Test
    void update_shouldThrow_whenNoExists() {
        long contractId = 1L;

        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> laborContractService.updateStatus(1L, LaborContractStatus.HIRED)
        );

        assertEquals("Contract with id " + contractId + " not found!", ex.getMessage());
        Mockito
                .verify(laborContractRepository)
                .findById(contractId);

        Mockito
                .verifyNoMoreInteractions(historyRepository);
    }

    @Test
    void update_shouldUpdateAndSave() {
        LaborContract existingContract = new LaborContract();
        existingContract.setId(1L);
        existingContract.setStatus(LaborContractStatus.NOT_SIGNED);

        LaborContract updatedContract = new LaborContract();
        updatedContract.setStatus(LaborContractStatus.HIRED);

        Mockito
                .when(laborContractRepository.findById(1L))
                .thenReturn(Optional.of(existingContract));

        laborContractService.update(1L, updatedContract);

        assertEquals(1L, existingContract.getId());
        assertEquals(LaborContractStatus.HIRED, existingContract.getStatus());
    }

    @Test
    void update_shouldThrow_whenNotExists() {
        long contractId = 1L;

        Mockito
                .when(laborContractRepository.findById(contractId))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> laborContractService.update(contractId, new LaborContract())
        );

        assertEquals("Contract with id " + contractId + " not found!", ex.getMessage());
        Mockito
                .verify(laborContractRepository)
                .findById(contractId);

        Mockito
                .verifyNoMoreInteractions(historyRepository);
    }

    @Test
    void merge() {
    }
}