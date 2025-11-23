package kz.zzhalelov.staffflow.server.earningType;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeCreateDto;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeHistoryMapper;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeMapper;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeResponseDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({EarningTypeController.class, EarningTypeMapper.class})
class EarningTypeControllerTest {
    @MockitoBean
    EarningTypeService earningTypeService;
    @MockitoBean
    EarningTypeRepository earningTypeRepository;
    @MockitoBean
    EarningTypeMapper typeMapper;
    @MockitoBean
    EarningTypeHistoryMapper typeHistoryMapper;

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
        createDto.setIsIndexable(true);

        // mapped entity
        EarningType mapped = new EarningType();
        mapped.setName("Test");

        // response DTO
        EarningTypeResponseDto responseDto = new EarningTypeResponseDto();
        responseDto.setId(earningTypeId);
        responseDto.setName("Test");

        Mockito
                .when(typeMapper.fromCreate(Mockito.any()))
                .thenReturn(mapped);

        Mockito
                .when(earningTypeService.create(Mockito.any(EarningType.class)))
                .thenAnswer(i -> {
                    EarningType argument = i.getArgument(0, EarningType.class);
                    argument.setId(earningTypeId);
                    return argument;
                });

        Mockito
                .when(typeMapper.toResponse(Mockito.any()))
                .thenReturn(responseDto);

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/earning-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(earningTypeId));
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
        type1.setIsIndexable(true);

        EarningType type2 = new EarningType();
        type2.setId(2L);
        type2.setName("Test");
        type2.setCode("Test");
        type2.setDescription("test description");
        type2.setIncludeInAverageSalaryCalc(true);
        type2.setIncludeInFot(true);
        type2.setIsIndexable(true);

        EarningTypeResponseDto dto1 = new EarningTypeResponseDto();
        dto1.setId(1L);
        dto1.setName("Test");
        dto1.setCode("Test");
        dto1.setDescription("test description");
        dto1.setIncludeInAverageSalaryCalc(true);
        dto1.setIncludeInFot(true);
        dto1.setIsIndexable(true);

        EarningTypeResponseDto dto2 = new EarningTypeResponseDto();
        dto2.setId(2L);
        dto2.setName("Test2");
        dto2.setCode("Test");
        dto2.setDescription("test description");
        dto2.setIncludeInAverageSalaryCalc(true);
        dto2.setIncludeInFot(true);
        dto2.setIsIndexable(true);

        Mockito
                .when(earningTypeService.findAll())
                .thenReturn(List.of(type1, type2));

        Mockito
                .when(typeMapper.toResponse(type1))
                .thenReturn(dto1);

        Mockito
                .when(typeMapper.toResponse(type2))
                .thenReturn(dto2);

        mockMvc.perform(get("/api/earning-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(type1.getId()))
                .andExpect(jsonPath("$[1].id").value(type2.getId()));
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void update() {
    }
}