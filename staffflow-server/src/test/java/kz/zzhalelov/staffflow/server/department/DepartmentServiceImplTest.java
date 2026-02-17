package kz.zzhalelov.staffflow.server.department;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Test
    void create_shouldCreate() {
        Department department = new Department();
        department.setId(1L);
        department.setName("abc");

        Mockito
                .when(departmentRepository.findByNameIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito
                .when(departmentRepository.save(Mockito.any(Department.class)))
                .thenReturn(department);

        Department savedDepartment = departmentService.create(department);

        assertEquals(department.getId(), savedDepartment.getId());
        assertEquals(department.getName(), savedDepartment.getName());
    }

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        Department existing = new Department();
        existing.setName("Marketing");

        Department newDepartment = new Department();
        newDepartment.setName("Marketing");

        Mockito
                .when(departmentRepository.findByNameIgnoreCase("Marketing"))
                .thenReturn(Optional.of(existing));

        assertThrows(ConflictException.class, () ->
                departmentService.create(newDepartment));
    }

    @Test
    void findAll_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Department existingDepartment1 = new Department();
        existingDepartment1.setId(1L);
        existingDepartment1.setName("abc");

        Department existingDepartment2 = new Department();
        existingDepartment2.setId(2L);
        existingDepartment2.setName("def");

        Page<Department> page = new PageImpl<>(List.of(existingDepartment1, existingDepartment2), pageable, 2);

        Mockito
                .when(departmentRepository.findAllByDeletedFalse(pageable))
                .thenReturn(page);

        Page<Department> savedDepartments = departmentService.findAll(pageable);
        assertEquals(2, savedDepartments.getContent().size());
        assertEquals(1, savedDepartments.getTotalPages());
        assertEquals(2, savedDepartments.getTotalElements());
        assertEquals(existingDepartment1.getId(), savedDepartments.getContent().get(0).getId());
        assertEquals(existingDepartment2.getId(), savedDepartments.getContent().get(1).getId());
    }

    @Test
    void findById() {
        Department existingDepartment = new Department();
        existingDepartment.setId(1L);
        existingDepartment.setName("abc");

        Mockito
                .when(departmentRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.of(existingDepartment));

        Department savedDepartment = departmentService.findById(1L);

        assertEquals(existingDepartment.getId(), savedDepartment.getId());
        assertEquals(existingDepartment.getName(), savedDepartment.getName());
    }

    @Test
    void findById_shouldThrow_whenDepartmentNotFound() {
        Mockito
                .when(departmentRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> departmentService.findById(1L)
        );
        Mockito
                .verify(departmentRepository)
                .findByIdAndDeletedFalse(1L);
    }

    @Test
    void update_shouldSaveDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("test");

        Mockito
                .when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        Mockito
                .when(departmentRepository.save(Mockito.any(Department.class)))
                .thenReturn(department);

        Department savedDepartment = departmentService.update(1L, department);
        assertEquals(department.getId(), savedDepartment.getId());
        assertEquals(department.getName(), savedDepartment.getName());
    }

    @Test
    void update_shouldThrow_whenDepartmentNotFound() {
        Mockito
                .when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> departmentService.update(1L, new Department())
        );
        assertEquals("Отдел не найден", ex.getMessage());

        Mockito
                .verify(departmentRepository)
                .findById(1L);
    }

    @Test
    void delete_shouldMarkAsDeleted_whenExists() {
        Department department = new Department();
        department.setId(1L);

        Mockito
                .when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        departmentService.delete(1L);

        assertTrue(department.isDeleted());
        assertNotNull(department.getDeletedAt());
        assertEquals("system", department.getDeletedBy());
    }

    @Test
    void delete_shouldThrow_whenNotExists() {
        Mockito
                .when(departmentRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> departmentService.delete(1L));
        Mockito.verify(departmentRepository, Mockito.never()).save(Mockito.any());
    }
}