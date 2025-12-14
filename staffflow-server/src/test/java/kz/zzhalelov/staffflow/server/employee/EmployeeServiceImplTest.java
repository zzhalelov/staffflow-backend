package kz.zzhalelov.staffflow.server.employee;

import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void create_shouldCreate() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Sarah");
        employee.setLastName("Connor");
        employee.setEmail("terminator@gmail.com");
        employee.setGender(GenderType.FEMALE);
        employee.setCitizenship("USA");
        employee.setPhone("+77011234567");
        employee.setIin("123456789012");
        employee.setAddress("Beverly Hills, CA");

        Mockito
                .when(employeeRepository.findByIin(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito
                .when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        Employee savedEmployee = employeeService.create(employee);

        assertEquals(employee.getId(), savedEmployee.getId());
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        assertEquals(employee.getIin(), savedEmployee.getIin());
        assertEquals(employee.getPhone(), savedEmployee.getPhone());
        assertEquals(employee.getCitizenship(), savedEmployee.getCitizenship());
        assertEquals(employee.getAddress(), savedEmployee.getAddress());
        assertEquals(employee.getGender(), savedEmployee.getGender());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
    }

    @Test
    void create_shouldThrow_whenIinAlreadyExists() {
        Employee existing = new Employee();
        existing.setIin("123456789012");

        Employee newEmployee = new Employee();
        newEmployee.setIin("123456789012");

        Mockito
                .when(employeeRepository.findByIin("123456789012"))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class, () ->
                employeeService.create(newEmployee));
    }

    @Test
    void create_shouldThrow_whenIinIsNull() {
        Employee employee = new Employee();
        employee.setIin(null);

        Mockito
                .when(employeeRepository.findByIin(null))
                .thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> employeeService.create(employee)
        );
        assertEquals("ИИН должен быть заполнен", exception.getMessage());
    }

    @Test
    void create_shouldThrow_whenIinIsBlank() {
        Employee employee = new Employee();
        employee.setIin("");

        Mockito
                .when(employeeRepository.findByIin(""))
                .thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> employeeService.create(employee)
        );
        assertEquals("ИИН должен быть заполнен", ex.getMessage());
    }

    @Test
    void create_shouldThrow_whenIinLengthNot12() {
        Employee employee = new Employee();
        employee.setIin("12345");

        Mockito
                .when(employeeRepository.findByIin("12345"))
                .thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> employeeService.create(employee));
        assertEquals("Длина ИИН должна быть 12 символов", exception.getMessage());
    }

    @Test
    void findAll_shouldReturnList() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Sarah");
        employee1.setLastName("Connor");
        employee1.setEmail("terminator@gmail.com");
        employee1.setGender(GenderType.FEMALE);
        employee1.setCitizenship("USA");
        employee1.setPhone("+77011234567");
        employee1.setIin("123456789012");
        employee1.setAddress("Beverly Hills, CA");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("John");
        employee2.setLastName("Connor");
        employee2.setEmail("hasta_la_vista_baby@gmail.com");
        employee2.setGender(GenderType.MALE);
        employee2.setCitizenship("USA");
        employee2.setPhone("+77014567890");
        employee2.setIin("098765432121");
        employee2.setAddress("Beverly Hills, CA");

        List<Employee> existingEmployees = List.of(employee1, employee2);

        Page<Employee> page = new PageImpl<>(existingEmployees);
        Mockito
                .when(employeeRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        List<Employee> savedEmployees = employeeService.findAll(0, 10);
        assertEquals(2, savedEmployees.size());
        assertEquals(existingEmployees.get(0).getId(), savedEmployees.get(0).getId());
        assertEquals(existingEmployees.get(0).getFirstName(), savedEmployees.get(0).getFirstName());
        assertEquals(existingEmployees.get(0).getLastName(), savedEmployees.get(0).getLastName());
        assertEquals(existingEmployees.get(0).getIin(), savedEmployees.get(0).getIin());
        assertEquals(existingEmployees.get(0).getPhone(), savedEmployees.get(0).getPhone());
        assertEquals(existingEmployees.get(0).getCitizenship(), savedEmployees.get(0).getCitizenship());
        assertEquals(existingEmployees.get(0).getAddress(), savedEmployees.get(0).getAddress());
        assertEquals(existingEmployees.get(0).getGender(), savedEmployees.get(0).getGender());
        assertEquals(existingEmployees.get(0).getEmail(), savedEmployees.get(0).getEmail());

        assertEquals(existingEmployees.get(1).getId(), savedEmployees.get(1).getId());
        assertEquals(existingEmployees.get(1).getFirstName(), savedEmployees.get(1).getFirstName());
        assertEquals(existingEmployees.get(1).getLastName(), savedEmployees.get(1).getLastName());
        assertEquals(existingEmployees.get(1).getIin(), savedEmployees.get(1).getIin());
        assertEquals(existingEmployees.get(1).getPhone(), savedEmployees.get(1).getPhone());
        assertEquals(existingEmployees.get(1).getCitizenship(), savedEmployees.get(1).getCitizenship());
        assertEquals(existingEmployees.get(1).getAddress(), savedEmployees.get(1).getAddress());
        assertEquals(existingEmployees.get(1).getGender(), savedEmployees.get(1).getGender());
        assertEquals(existingEmployees.get(1).getEmail(), savedEmployees.get(1).getEmail());
    }

    @Test
    void findById() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Sarah");
        employee.setLastName("Connor");
        employee.setEmail("terminator@gmail.com");
        employee.setGender(GenderType.FEMALE);
        employee.setCitizenship("USA");
        employee.setPhone("+77011234567");
        employee.setIin("123456789012");
        employee.setAddress("Beverly Hills, CA");

        Mockito
                .when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee savedEmployee = employeeService.findById(1L);
        assertEquals(employee.getId(), savedEmployee.getId());
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        assertEquals(employee.getGender(), savedEmployee.getGender());
        assertEquals(employee.getCitizenship(), savedEmployee.getCitizenship());
        assertEquals(employee.getPhone(), savedEmployee.getPhone());
        assertEquals(employee.getIin(), savedEmployee.getIin());
        assertEquals(employee.getAddress(), savedEmployee.getAddress());
    }

    @Test
    void findById_shouldThrow_whenEmployeeNotFound() {
        Mockito
                .when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> employeeService.findById(1L)
        );
        Mockito
                .verify(employeeRepository).findById(1L);
    }

    @Test
    void update_shouldThrow_whenEmployeeNotFound() {
        Mockito
                .when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> employeeService.update(1L, new Employee())
        );
        assertEquals("Employee not found", ex.getMessage());

        Mockito
                .verify(employeeRepository)
                .findById(1L);
    }

    @Test
    void update_shouldUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Sarah");
        employee.setLastName("Connor");
        employee.setEmail("terminator@gmail.com");
        employee.setGender(GenderType.FEMALE);
        employee.setCitizenship("USA");
        employee.setPhone("+77011234567");
        employee.setIin("123456789012");
        employee.setAddress("Beverly Hills, CA");

        Mockito
                .when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Mockito
                .when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        Employee savedEmployee = employeeService.update(1L, employee);
        assertEquals(employee.getId(), savedEmployee.getId());
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        assertEquals(employee.getGender(), savedEmployee.getGender());
        assertEquals(employee.getCitizenship(), savedEmployee.getCitizenship());
        assertEquals(employee.getPhone(), savedEmployee.getPhone());
        assertEquals(employee.getIin(), savedEmployee.getIin());
        assertEquals(employee.getAddress(), savedEmployee.getAddress());
    }

    @Test
    void update_shouldOnlyUpdateNonNullFields() {
        long employeeId = 1L;

        Employee existing = new Employee();
        existing.setId(employeeId);
        existing.setFirstName("Old First");
        existing.setLastName("Old Last");
        existing.setEmail("old@email.com");
        existing.setIin("123456789012");
        existing.setPhone("+7 111 111 1111");
        existing.setAddress("Old Address");
        existing.setCitizenship("KZ");
        existing.setGender(GenderType.MALE);

        Employee updated = new Employee();
        // Only update firstName, everything else is null
        //updated.setFirstName("New First");
        //Nothing changed. All fields are null

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(existing));

        Mockito
                .when(employeeRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        Employee result = employeeService.update(employeeId, updated);
        // Only firstName should change
        //assertEquals("New First", result.getFirstName());
        //Nothing changed. All fields are null
        assertEquals("Old First", result.getFirstName());
        // Everything else should remain unchanged
        assertEquals("Old Last", result.getLastName());
        assertEquals("old@email.com", result.getEmail());
        assertEquals("123456789012", result.getIin());
        assertEquals("+7 111 111 1111", result.getPhone());
        assertEquals("Old Address", result.getAddress());
        assertEquals("KZ", result.getCitizenship());
        assertEquals(GenderType.MALE, result.getGender());
    }

    @Test
    void update_shouldNotUpdateBlankFields() {
        long employeeId = 1L;

        Employee existing = new Employee();
        existing.setId(employeeId);
        existing.setFirstName("Old First");
        existing.setLastName("Old Last");
        existing.setEmail("old@email.com");
        existing.setGender(GenderType.MALE);
        existing.setCitizenship("US");
        existing.setAddress("Old Address");
        existing.setPhone("+7 777 111 11 11");
        existing.setIin("123456789012");

        Employee updated = new Employee();
        updated.setFirstName(" ");
        updated.setEmail("   ");
        updated.setLastName("");
        updated.setCitizenship(" ");
        updated.setAddress(" ");
        updated.setPhone(" ");
        updated.setIin(" ");

        Mockito
                .when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(existing));
        Mockito
                .when(employeeRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        Employee result = employeeService.update(employeeId, updated);

        assertEquals("Old First", result.getFirstName()); // Should remain unchanged
        assertEquals("old@email.com", result.getEmail()); // Should remain unchanged
        assertEquals("Old Last", result.getLastName());
        assertEquals(GenderType.MALE, result.getGender());
        assertEquals("US", result.getCitizenship());
        assertEquals("Old Address", result.getAddress());
        assertEquals("+7 777 111 11 11", result.getPhone());
        assertEquals("123456789012", result.getIin());
    }

    @Test
    void findByLastNameContainingIgnoreCase() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Sarah");
        employee1.setLastName("Connor");
        employee1.setEmail("terminator@gmail.com");
        employee1.setGender(GenderType.FEMALE);
        employee1.setCitizenship("USA");
        employee1.setPhone("+77011234567");
        employee1.setIin("123456789012");
        employee1.setAddress("Beverly Hills, CA");

        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setFirstName("John");
        employee2.setLastName("Connor");
        employee2.setEmail("hasta_la_vista_baby@gmail.com");
        employee2.setGender(GenderType.MALE);
        employee2.setCitizenship("USA");
        employee2.setPhone("+77014567890");
        employee2.setIin("098765432121");
        employee2.setAddress("Beverly Hills, CA");

        Mockito
                .when(employeeRepository.findByLastNameContainingIgnoreCase("Connor"))
                .thenReturn(List.of(employee1, employee2));

        List<Employee> result = employeeService.findByLastNameContainingIgnoreCase("Connor");
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> e.getLastName().equalsIgnoreCase("Connor")));
        assertEquals("Sarah", result.get(0).getFirstName());
        assertEquals("John", result.get(1).getFirstName());
        Mockito.verify(employeeRepository).findByLastNameContainingIgnoreCase("Connor");
    }

    @Test
    void delete_shouldThrow_WhenNotFound() {
        Mockito
                .when(employeeRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(NotFoundException.class, () -> employeeService.delete(1L));
        Mockito.verify(employeeRepository, Mockito.never()).deleteById(1L);
    }

    @Test
    void delete_shouldDelete_whenEmployeeExists() {
        long employeeId = 1L;

        Mockito
                .when(employeeRepository.existsById(employeeId))
                .thenReturn(true);

        assertDoesNotThrow(() -> employeeService.delete(employeeId));
        Mockito.verify(employeeRepository).deleteById(employeeId);
    }
}