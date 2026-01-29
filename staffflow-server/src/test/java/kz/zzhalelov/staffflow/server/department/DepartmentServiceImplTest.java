//package kz.zzhalelov.staffflow.server.department;
//
//import kz.zzhalelov.staffflow.server.exception.NotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class DepartmentServiceImplTest {
//    @Mock
//    private DepartmentRepository departmentRepository;
//    @InjectMocks
//    DepartmentServiceImpl departmentService;
//
//    @Test
//    void create_shouldCreate() {
//        Department department = new Department();
//        department.setId(1L);
//        department.setName("abc");
//
//        Mockito
//                .when(departmentRepository.save(Mockito.any(Department.class)))
//                .thenReturn(department);
//
//        Department savedDepartment = departmentService.create(department);
//
//        assertEquals(department.getId(), savedDepartment.getId());
//        assertEquals(department.getName(), savedDepartment.getName());
//    }
//
//    @Test
//    void findAll_shouldReturnList() {
//        Department existingDepartment1 = new Department();
//        existingDepartment1.setId(1L);
//        existingDepartment1.setName("abc");
//
//        Department existingDepartment2 = new Department();
//        existingDepartment2.setId(2L);
//        existingDepartment2.setName("def");
//
//        List<Department> existingDepartments = List.of(existingDepartment1, existingDepartment2);
//
//        Mockito
//                .when(departmentRepository.findAll())
//                .thenReturn(existingDepartments);
//
//        List<Department> savedDepartments = departmentService.findAll();
//        assertEquals(2, savedDepartments.size());
//        assertEquals(existingDepartment1.getId(), savedDepartments.get(0).getId());
//        assertEquals(existingDepartment2.getId(), savedDepartments.get(1).getId());
//    }
//
//    @Test
//    void findById() {
//        Department existingDepartment = new Department();
//        existingDepartment.setId(1L);
//        existingDepartment.setName("abc");
//
//        Mockito
//                .when(departmentRepository.findById(1L))
//                .thenReturn(Optional.of(existingDepartment));
//
//        Department savedDepartment = departmentService.findById(1L);
//
//        assertEquals(existingDepartment.getId(), savedDepartment.getId());
//        assertEquals(existingDepartment.getName(), savedDepartment.getName());
//    }
//
//    @Test
//    void findById_shouldThrow_whenDepartmentNotFound() {
//        Mockito
//                .when(departmentRepository.findById(1L))
//                .thenReturn(Optional.empty());
//
//        assertThrows(
//                NotFoundException.class,
//                () -> departmentService.findById(1L)
//        );
//        Mockito
//                .verify(departmentRepository)
//                .findById(1L);
//    }
//
//    @Test
//    void update_shouldSaveDepartment() {
//        Department department = new Department();
//        department.setId(1L);
//        department.setName("test");
//
//        Mockito
//                .when(departmentRepository.findById(1L))
//                .thenReturn(Optional.of(department));
//
//        Mockito
//                .when(departmentRepository.save(Mockito.any(Department.class)))
//                .thenReturn(department);
//
//        Department savedDepartment = departmentService.update(1L, department);
//        assertEquals(department.getId(), savedDepartment.getId());
//        assertEquals(department.getName(), savedDepartment.getName());
//    }
//
//    @Test
//    void delete_shouldDelete_whenExists() {
//        Mockito
//                .when(departmentRepository.findById(1L))
//                .thenReturn(Optional.of(new Department()));
//        departmentService.delete(1L);
//
//        Mockito.verify(departmentRepository).deleteById(1L);
//    }
//
//    @Test
//    void delete_shouldThrow_whenNoExists() {
//        Mockito
//                .when(departmentRepository.findById(1L))
//                .thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> departmentService.delete(1L));
//        Mockito.verify(departmentRepository, Mockito.never()).deleteById(1L);
//    }
//}