package kz.zzhalelov.staffflow.server.earningType;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.config.NoSecurityTestConfig;
import kz.zzhalelov.staffflow.server.earningType.dto.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({EarningTypeController.class, EarningTypeMapper.class, EarningTypeHistoryMapper.class})
@Import(NoSecurityTestConfig.class)
class EarningTypeControllerTest {
    @MockitoBean
    EarningTypeService earningTypeService;
    @MockitoBean
    EarningTypeRepository earningTypeRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        long earningTypeId = 1;

        EarningTypeCreateDto createDto = new EarningTypeCreateDto();
        createDto.setName("Test");
        createDto.setCode("Test");
        createDto.setDescription("test description");
        createDto.setIncludeInAverageSalaryCalc(true);
        createDto.setIncludeInFot(true);
        createDto.setIndexable(true);

        Mockito
                .when(earningTypeService.create(Mockito.any(EarningType.class)))
                .thenAnswer(i -> {
                    EarningType argument = i.getArgument(0, EarningType.class);
                    argument.setId(earningTypeId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/earning-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(earningTypeId))
                .andExpect(jsonPath("$.name").value(createDto.getName()))
                .andExpect(jsonPath("$.code").value(createDto.getCode()))
                .andExpect(jsonPath("$.description").value(createDto.getDescription()));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        EarningType type1 = new EarningType();
        type1.setId(1L);
        type1.setName("Test");
        type1.setCode("Test");
        type1.setDescription("test description");
        type1.setIncludeInAverageSalaryCalc(true);
        type1.setIncludeInFot(true);
        type1.setIndexable(true);

        EarningType type2 = new EarningType();
        type2.setId(2L);
        type2.setName("Test");
        type2.setCode("Test");
        type2.setDescription("test description");
        type2.setIncludeInAverageSalaryCalc(true);
        type2.setIncludeInFot(true);
        type2.setIndexable(true);

        Page<EarningType> page =
                new PageImpl<>(
                        List.of(type1, type2),
                        PageRequest.of(0, 20),
                        2
                );

        Mockito
                .when(earningTypeService.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/earning-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(type1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(type2.getId()));
    }

    @Test
    @SneakyThrows
    void findById() {
        long typeId = 1;

        EarningType earningType = new EarningType();
        earningType.setId(1L);
        earningType.setName("test");
        earningType.setCode("test");
        earningType.setIndexable(true);
        earningType.setIncludeInFot(true);
        earningType.setIncludeInAverageSalaryCalc(true);
        earningType.setDescription("test");

        Mockito
                .when(earningTypeService.findById(Mockito.anyLong()))
                .thenReturn(earningType);

        mockMvc.perform(get("/api/earning-types/" + typeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(typeId))
                .andExpect(jsonPath("$.name").value(earningType.getName()))
                .andExpect(jsonPath("$.code").value(earningType.getCode()));
    }

    @Test
    @SneakyThrows
    void delete_Success() {
        long earningTypeId = 1;

        Mockito
                .doNothing()
                .when(earningTypeService)
                .delete(earningTypeId);

        mockMvc.perform(delete("/api/earning-types/" + earningTypeId))
                .andExpect(status().isNoContent());

        Mockito
                .verify(earningTypeService)
                .delete(earningTypeId);
    }

    @Test
    @SneakyThrows
    void getHistory() {
        long earningTypeId = 1L;

        EarningType type = new EarningType();
        type.setId(earningTypeId);

        EarningTypeHistory history1 = new EarningTypeHistory();
        history1.setId(1L);
        history1.setEarningType(type);
        history1.setStartDate(LocalDate.of(2025, 1, 1));
        history1.setEndDate(LocalDate.of(2025, 12, 31));
        history1.setComment("test");
        history1.setIncludeInAverageSalaryCalc(true);
        history1.setIncludeInFot(true);
        history1.setIsIndexable(true);

        EarningTypeHistory history2 = new EarningTypeHistory();
        history2.setId(2L);
        history2.setEarningType(type);
        history2.setStartDate(LocalDate.of(2025, 1, 1));
        history2.setEndDate(LocalDate.of(2025, 12, 31));
        history2.setComment("test diff");
        history2.setIncludeInAverageSalaryCalc(true);
        history2.setIncludeInFot(true);
        history2.setIsIndexable(true);

        Mockito
                .when(earningTypeService.findHistoryByEarningTypeId(earningTypeId))
                .thenReturn(List.of(history1, history2));

        mockMvc.perform(get("/api/earning-types/" + earningTypeId + "/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].comment").value("test diff"));

        Mockito
                .verify(earningTypeService).findHistoryByEarningTypeId(earningTypeId);
    }

    @Test
    @SneakyThrows
    void update_shouldReturnUpdatedEarningType() {
        long earningTypeId = 1L;

        EarningTypeUpdateDto updateDto = new EarningTypeUpdateDto();
        updateDto.setName("Updated Name");
        updateDto.setCode("UPDATED");
        updateDto.setDescription("updated description");
        updateDto.setIncludeInAverageSalaryCalc(true);
        updateDto.setIncludeInFot(true);
        updateDto.setIndexable(true);

        EarningType type = new EarningType();
        type.setId(earningTypeId);
        type.setName("Updated Name");
        type.setCode("UPDATED");
        type.setDescription("updated description");
        type.setIncludeInAverageSalaryCalc(true);
        type.setIncludeInFot(true);
        type.setIndexable(true);

        Mockito
                .when(earningTypeRepository.findById(Mockito.eq(earningTypeId)))
                .thenReturn(Optional.of(new EarningType()));

        Mockito
                .when(earningTypeService.update(Mockito.eq(earningTypeId), Mockito.any(EarningType.class)))
                .thenReturn(type);

        String json = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(patch("/api/earning-types/" + earningTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(earningTypeId))
                .andExpect(jsonPath("$.name").value(type.getName()));
    }

    @Test
    @SneakyThrows
    void restore_shouldReturnNoContent() {
        long id = 1L;

        Mockito
                .doNothing()
                .when(earningTypeService)
                .restore(id);

        mockMvc.perform(post("/api/earning-types/{id}/restore", id))
                .andExpect(status().isNoContent());

        Mockito
                .verify(earningTypeService, Mockito.times(1))
                .restore(id);
    }
}