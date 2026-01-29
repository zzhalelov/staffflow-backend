package kz.zzhalelov.staffflow.server.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.config.SecurityConfig;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeCreateDto;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeMapper;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeUpdateDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;

@WithMockUser(username = "admin", roles = {"ADMIN"})
@WebMvcTest({EmployeeController.class, EmployeeMapper.class})
@Import(SecurityConfig.class)
class EmployeeControllerTest {
    @MockitoBean
    EmployeeService employeeService;
    @MockitoBean
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setIin("123456789012");
        employee1.setPhone("+77011234567");
        employee1.setCitizenship("USA");
        employee1.setGender(GenderType.MALE);
        employee1.setEmail("abc@gmail.com");
        employee1.setFirstName("John");
        employee1.setLastName("Deer");
        employee1.setAddress("test avenue");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setIin("123456789012");
        employee2.setPhone("+77011234567");
        employee2.setCitizenship("USA");
        employee2.setGender(GenderType.MALE);
        employee2.setEmail("abc@gmail.com");
        employee2.setFirstName("John");
        employee2.setLastName("Deer");
        employee2.setAddress("test avenue");

        Mockito
                .when(employeeService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(employee1, employee2));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()));
    }

    @Test
    @SneakyThrows
    void findById() {
        long employeeId = 1;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Ben");
        employee.setLastName("Afflek");
        employee.setAddress("test address");
        employee.setCitizenship("USA");
        employee.setEmail("test@gmail.com");
        employee.setGender(GenderType.MALE);
        employee.setIin("123456789012");
        employee.setPhone("77011234567");

        Mockito
                .when(employeeService.findById(Mockito.anyLong()))
                .thenReturn(employee);

        mockMvc.perform(get("/api/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId));
    }

    @Test
    @SneakyThrows
    void create_ShouldReturnCreated() {
        long employeeId = 1;

        EmployeeCreateDto createDto = new EmployeeCreateDto();
        createDto.setFirstName("Ben");
        createDto.setLastName("Afflek");
        createDto.setAddress("test address");
        createDto.setCitizenship("USA");
        createDto.setEmail("test@gmail.com");
        createDto.setGender(GenderType.MALE);
        createDto.setIin("123456789012");
        createDto.setPhone("77011234567");

        Mockito
                .when(employeeService.create(Mockito.any(Employee.class)))
                .thenAnswer(i -> {
                    Employee argument = i.getArgument(0, Employee.class);
                    argument.setId(employeeId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeId));
    }

    @Test
    @SneakyThrows
    void update_shouldReturnOk() {
        long employeeId = 1;

        EmployeeUpdateDto updateDto = new EmployeeUpdateDto();
        updateDto.setFirstName("John");

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");

        Mockito
                .when(employeeRepository.findById(Mockito.eq(employeeId)))
                .thenReturn(Optional.of(new Employee()));

        Mockito
                .when(employeeService.update(Mockito.anyLong(), Mockito.any(Employee.class)))
                .thenReturn(employee);

        String json = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(patch("/api/employees/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId));
    }

    @Test
    @SneakyThrows
    void delete_Success() {
        long employeeId = 1;

        Mockito
                .doNothing()
                .when(employeeService)
                .delete(employeeId);

        mockMvc.perform(delete("/api/employees/{employeeId}", employeeId))
                .andExpect(status().isNoContent());

        Mockito
                .verify(employeeService)
                .delete(employeeId);
    }

    @Test
    @SneakyThrows
    void findByLastName() {
        String lastName = "Afflek";

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Ben");
        employee1.setLastName("Afflek");
        employee1.setAddress("test address");
        employee1.setCitizenship("USA");
        employee1.setEmail("test@gmail.com");
        employee1.setGender(GenderType.MALE);
        employee1.setIin("123456789012");
        employee1.setPhone("77011234567");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Steve");
        employee2.setLastName("Afflek");
        employee2.setAddress("test address");
        employee2.setCitizenship("USA");
        employee2.setEmail("test@gmail.com");
        employee2.setGender(GenderType.MALE);
        employee2.setIin("123456789012");
        employee2.setPhone("77011234567");

        Mockito
                .when(employeeService.findByLastNameContainingIgnoreCase(Mockito.anyString()))
                .thenReturn(List.of(employee1, employee2));

        mockMvc.perform(get("/api/employees/find-by-lastname/" + lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()));
    }
}