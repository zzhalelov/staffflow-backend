package kz.zzhalelov.staffflow.server.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentCreateDto;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentMapper;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentUpdateDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({DepartmentController.class, DepartmentMapper.class})
class DepartmentControllerTest {
    @MockitoBean
    DepartmentService departmentService;
    @MockitoBean
    DepartmentRepository departmentRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        long departmentId = 1;

        DepartmentCreateDto createDto = new DepartmentCreateDto();
        createDto.setName("abc");

        Mockito
                .when(departmentService.create(Mockito.any(Department.class)))
                .thenAnswer(i -> {
                    Department argument = i.getArgument(0, Department.class);
                    argument.setId(departmentId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(createDto.getName()));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("abc");

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("def");

        Mockito
                .when(departmentService.findAll())
                .thenReturn(List.of(department1, department2));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(department1.getId()))
                .andExpect(jsonPath("$[0].name").value(department1.getName()))
                .andExpect(jsonPath("$[1].id").value(department2.getId()))
                .andExpect(jsonPath("$[1].name").value(department2.getName()));
    }

    @Test
    @SneakyThrows
    void findById() {
        long departmentId = 1;

        Department department = new Department();
        department.setId(1L);
        department.setName("abc");

        Mockito
                .when(departmentService.findById(Mockito.anyLong()))
                .thenReturn(department);

        mockMvc.perform(get("/api/departments/" + departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(department.getName()));
    }

    @Test
    @SneakyThrows
    void update_shouldReturnOk() {
        long departmentId = 1L;

        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto();
        departmentUpdateDto.setName("updatedName");

        Department department = new Department();
        department.setId(departmentId);
        department.setName("updatedName");

        Mockito.when(departmentRepository.findById(Mockito.eq(departmentId)))
                .thenReturn(Optional.of(new Department()));

        Mockito
                .when(departmentService.update(Mockito.any(Department.class)))
                .thenReturn(department);

        String json = objectMapper.writeValueAsString(departmentUpdateDto);

        mockMvc.perform(patch("/api/departments/" + departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(department.getName()));
    }

    @Test
    @SneakyThrows
    void deleteById() {
        long departmentId = 1L;

        mockMvc.perform(delete("/api/departments/" + departmentId))
                .andExpect(status().isNoContent());
    }
}