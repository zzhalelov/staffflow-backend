package kz.zzhalelov.staffflow.server.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({EmployeeController.class, EmployeeMapper.class})
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
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByLastName() {
    }
}