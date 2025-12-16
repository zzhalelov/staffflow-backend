package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorCreateDto;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorMapper;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorUpdateDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebMvcTest({RegulatoryIndicatorController.class, RegulatoryIndicatorMapper.class})
class RegulatoryIndicatorControllerTest {
    @MockitoBean
    RegulatoryIndicatorService indicatorService;
    @MockitoBean
    RegulatoryIndicatorRepository indicatorRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        long indicatorId = 1L;

        RegulatoryIndicatorCreateDto createDto = new RegulatoryIndicatorCreateDto();
        createDto.setMzpValue(BigDecimal.valueOf(85000));
        createDto.setMrpValue(BigDecimal.valueOf(3932));
        createDto.setDate(LocalDate.of(2025, 1, 1));
        createDto.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorService.create(Mockito.any(RegulatoryIndicator.class)))
                .thenAnswer(i -> {
                    RegulatoryIndicator argument = i.getArgument(0, RegulatoryIndicator.class);
                    argument.setId(indicatorId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/indicators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(indicatorId));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        RegulatoryIndicator indicator1 = new RegulatoryIndicator();
        indicator1.setId(1L);
        indicator1.setDate(LocalDate.of(2024, 1, 1));
        indicator1.setMrpValue(BigDecimal.valueOf(3692));
        indicator1.setMzpValue(BigDecimal.valueOf(85000));
        indicator1.setDescription("МРП и МЗП на 2024 год");

        RegulatoryIndicator indicator2 = new RegulatoryIndicator();
        indicator2.setId(2L);
        indicator2.setDate(LocalDate.of(2025, 1, 1));
        indicator2.setMrpValue(BigDecimal.valueOf(3932));
        indicator2.setMzpValue(BigDecimal.valueOf(85000));
        indicator2.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorService.findAll())
                .thenReturn(List.of(indicator1, indicator2));

        mockMvc.perform(get("/api/indicators"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(indicator1.getId()))
                .andExpect(jsonPath("$[1].id").value(indicator2.getId()));
    }

    @Test
    @SneakyThrows
    void findById() {
        long indicatorId = 1;

        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setId(1L);
        indicator.setDate(LocalDate.of(2024, 1, 1));
        indicator.setMrpValue(BigDecimal.valueOf(3692));
        indicator.setMzpValue(BigDecimal.valueOf(85000));
        indicator.setDescription("МРП и МЗП на 2024 год");

        Mockito
                .when(indicatorService.findById(Mockito.anyLong()))
                .thenReturn(indicator);

        mockMvc.perform(get("/api/indicators/" + indicatorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(indicatorId));
    }

    @Test
    @SneakyThrows
    void delete_Success() {
        long indicatorId = 1;

        Mockito
                .doNothing()
                .when(indicatorService)
                .deleteById(indicatorId);

        mockMvc.perform(delete("/api/indicators/" + indicatorId))
                .andExpect(status().isNoContent());

        Mockito
                .verify(indicatorService)
                .deleteById(indicatorId);
    }

    @Test
    @SneakyThrows
    void update() {
        long indicatorId = 1L;

        RegulatoryIndicatorUpdateDto dto = new RegulatoryIndicatorUpdateDto();
        dto.setDate(LocalDate.of(2025, 1, 1));
        dto.setMrpValue(BigDecimal.valueOf(3932));
        dto.setMzpValue(BigDecimal.valueOf(85000));
        dto.setDescription("МРП и МЗП на 2025 год");

        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setId(indicatorId);
        indicator.setDate(LocalDate.of(2025, 1, 1));
        indicator.setMrpValue(BigDecimal.valueOf(3932));
        indicator.setMzpValue(BigDecimal.valueOf(85000));
        indicator.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorService.update(Mockito.eq(indicatorId), Mockito.any(RegulatoryIndicator.class)))
                .thenReturn(indicator);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/indicators/" + indicatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(indicatorId));
    }
}