//package kz.zzhalelov.staffflow.server.earningType;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kz.zzhalelov.staffflow.server.config.NoSecurityTestConfig;
//import kz.zzhalelov.staffflow.server.config.SecurityConfig;
//import kz.zzhalelov.staffflow.server.earningType.dto.*;
//import kz.zzhalelov.staffflow.server.employee.EmployeeController;
//import kz.zzhalelov.staffflow.server.organization.OrganizationController;
//import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
//import kz.zzhalelov.staffflow.server.security.JwtAuthenticationFilter;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(
//        controllers = EarningTypeController.class,
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                classes = JwtAuthenticationFilter.class
//        )
//)
//@Import(NoSecurityTestConfig.class)
//@AutoConfigureMockMvc
//class EarningTypeControllerTest {
//    @MockitoBean
//    EarningTypeService earningTypeService;
//    @MockitoBean
//    EarningTypeRepository earningTypeRepository;
//    @MockitoBean
//    EarningTypeMapper typeMapper;
//    @MockitoBean
//    EarningTypeHistoryMapper typeHistoryMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @SneakyThrows
//    void create_shouldReturnCreated() {
//        long earningTypeId = 1;
//
//        EarningTypeCreateDto createDto = new EarningTypeCreateDto();
//        createDto.setName("Test");
//        createDto.setCode("Test");
//        createDto.setDescription("test description");
//        createDto.setIncludeInAverageSalaryCalc(true);
//        createDto.setIncludeInFot(true);
//        createDto.setIsIndexable(true);
//
//        // mapped entity
//        EarningType mapped = new EarningType();
//        mapped.setName("Test");
//
//        // response DTO
//        EarningTypeResponseDto responseDto = new EarningTypeResponseDto();
//        responseDto.setId(earningTypeId);
//        responseDto.setName("Test");
//
//        Mockito
//                .when(typeMapper.fromCreate(Mockito.any()))
//                .thenReturn(mapped);
//
//        Mockito
//                .when(earningTypeService.create(Mockito.any(EarningType.class)))
//                .thenAnswer(i -> {
//                    EarningType argument = i.getArgument(0, EarningType.class);
//                    argument.setId(earningTypeId);
//                    return argument;
//                });
//
//        Mockito
//                .when(typeMapper.toResponse(Mockito.any()))
//                .thenReturn(responseDto);
//
//        String json = objectMapper.writeValueAsString(createDto);
//
//        mockMvc.perform(post("/api/earning-types")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(earningTypeId));
//    }
//
//    @Test
//    @SneakyThrows
//    void findAll_shouldReturnNonEmptyList() {
//        EarningType type1 = new EarningType();
//        type1.setId(1L);
//        type1.setName("Test");
//        type1.setCode("Test");
//        type1.setDescription("test description");
//        type1.setIncludeInAverageSalaryCalc(true);
//        type1.setIncludeInFot(true);
//        type1.setIsIndexable(true);
//
//        EarningType type2 = new EarningType();
//        type2.setId(2L);
//        type2.setName("Test");
//        type2.setCode("Test");
//        type2.setDescription("test description");
//        type2.setIncludeInAverageSalaryCalc(true);
//        type2.setIncludeInFot(true);
//        type2.setIsIndexable(true);
//
//        EarningTypeResponseDto dto1 = new EarningTypeResponseDto();
//        dto1.setId(1L);
//        dto1.setName("Test");
//        dto1.setCode("Test");
//        dto1.setDescription("test description");
//        dto1.setIncludeInAverageSalaryCalc(true);
//        dto1.setIncludeInFot(true);
//        dto1.setIsIndexable(true);
//
//        EarningTypeResponseDto dto2 = new EarningTypeResponseDto();
//        dto2.setId(2L);
//        dto2.setName("Test2");
//        dto2.setCode("Test");
//        dto2.setDescription("test description");
//        dto2.setIncludeInAverageSalaryCalc(true);
//        dto2.setIncludeInFot(true);
//        dto2.setIsIndexable(true);
//
//        Mockito
//                .when(earningTypeService.findAll())
//                .thenReturn(List.of(type1, type2));
//
//        Mockito
//                .when(typeMapper.toResponse(type1))
//                .thenReturn(dto1);
//
//        Mockito
//                .when(typeMapper.toResponse(type2))
//                .thenReturn(dto2);
//
//        mockMvc.perform(get("/api/earning-types"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(type1.getId()))
//                .andExpect(jsonPath("$[1].id").value(type2.getId()));
//    }
//
//    @Test
//    @SneakyThrows
//    void findById() {
//        long typeId = 1;
//
//        EarningType earningType = new EarningType();
//        earningType.setId(1L);
//        earningType.setName("test");
//        earningType.setCode("test");
//        earningType.setIsIndexable(true);
//        earningType.setIncludeInFot(true);
//        earningType.setIncludeInAverageSalaryCalc(true);
//        earningType.setDescription("test");
//
//        EarningTypeResponseDto responseDto = new EarningTypeResponseDto();
//        responseDto.setId(1L);
//        responseDto.setName("test");
//        responseDto.setCode("test");
//        responseDto.setIsIndexable(true);
//        responseDto.setIncludeInFot(true);
//        responseDto.setIncludeInAverageSalaryCalc(true);
//        responseDto.setDescription("test");
//
//        Mockito
//                .when(earningTypeService.findById(Mockito.anyLong()))
//                .thenReturn(earningType);
//
//        Mockito
//                .when(typeMapper.toResponse(earningType))
//                .thenReturn(responseDto);
//
//        mockMvc.perform(get("/api/earning-types/" + typeId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(typeId));
//    }
//
//    @Test
//    @SneakyThrows
//    void delete_Success() {
//        long earningTypeId = 1;
//
//        Mockito
//                .doNothing()
//                .when(earningTypeService)
//                .delete(earningTypeId);
//
//        mockMvc.perform(delete("/api/earning-types/" + earningTypeId))
//                .andExpect(status().isNoContent());
//
//        Mockito
//                .verify(earningTypeService)
//                .delete(earningTypeId);
//    }
//
//    @Test
//    @SneakyThrows
//    void getHistory() {
//        long earningTypeId = 1L;
//
//        EarningType type = new EarningType();
//        type.setId(earningTypeId);
//
//        EarningTypeHistory history1 = new EarningTypeHistory();
//        history1.setId(1L);
//        history1.setEarningType(type);
//        history1.setStartDate(LocalDate.of(2025, 1, 1));
//        history1.setEndDate(LocalDate.of(2025, 12, 31));
//        history1.setComment("test");
//        history1.setIncludeInAverageSalaryCalc(true);
//        history1.setIncludeInFot(true);
//        history1.setIsIndexable(true);
//
//        EarningTypeHistory history2 = new EarningTypeHistory();
//        history2.setId(2L);
//        history2.setEarningType(type);
//        history1.setStartDate(LocalDate.of(2025, 1, 1));
//        history1.setEndDate(LocalDate.of(2025, 12, 31));
//        history1.setComment("test diff");
//        history1.setIncludeInAverageSalaryCalc(true);
//        history1.setIncludeInFot(true);
//        history1.setIsIndexable(true);
//
//        EarningTypeHistoryResponseDto dto1 = new EarningTypeHistoryResponseDto();
//        dto1.setId(1L);
//        dto1.setEarningType(type);
//        dto1.setStartDate(LocalDate.of(2025, 1, 1));
//        dto1.setEndDate(LocalDate.of(2025, 12, 31));
//        dto1.setComment("test");
//        dto1.setIncludeInAverageSalaryCalc(true);
//        dto1.setIncludeInFot(true);
//        dto1.setIsIndexable(true);
//
//        EarningTypeHistoryResponseDto dto2 = new EarningTypeHistoryResponseDto();
//        dto2.setId(2L);
//        dto2.setEarningType(type);
//        dto2.setStartDate(LocalDate.of(2025, 1, 1));
//        dto2.setEndDate(LocalDate.of(2025, 12, 31));
//        dto2.setComment("test");
//        dto2.setIncludeInAverageSalaryCalc(true);
//        dto2.setIncludeInFot(true);
//        dto2.setIsIndexable(true);
//
//        Mockito
//                .when(earningTypeService.findHistoryByEarningTypeId(earningTypeId))
//                .thenReturn(List.of(history1, history2));
//
//        Mockito
//                .when(typeHistoryMapper.toResponse(history1))
//                .thenReturn(dto1);
//
//        Mockito
//                .when(typeHistoryMapper.toResponse(history2))
//                .thenReturn(dto2);
//
//        mockMvc.perform(get("/api/earning-types/" + earningTypeId + "/history"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[1].id").value(2L));
//
//        Mockito
//                .verify(earningTypeService).findHistoryByEarningTypeId(earningTypeId);
//    }
//
//    @Test
//    @SneakyThrows
//    void update_shouldReturnUpdatedEarningType() {
//        long earningTypeId = 1L;
//
//        EarningTypeUpdateDto dto = new EarningTypeUpdateDto();
//        dto.setName("Updated Name");
//        dto.setCode("UPDATED");
//        dto.setDescription("updated description");
//        dto.setIncludeInAverageSalaryCalc(true);
//        dto.setIncludeInFot(true);
//        dto.setIsIndexable(true);
//
//        EarningType type = new EarningType();
//        type.setName("Updated Name");
//        type.setCode("UPDATED");
//        type.setDescription("updated description");
//        type.setIncludeInAverageSalaryCalc(true);
//        type.setIncludeInFot(true);
//        type.setIsIndexable(true);
//
//        EarningType savedType = new EarningType();
//        savedType.setId(earningTypeId);
//        savedType.setName("Updated Name");
//        savedType.setCode("UPDATED");
//        savedType.setDescription("updated description");
//        savedType.setIncludeInAverageSalaryCalc(true);
//        savedType.setIncludeInFot(true);
//        savedType.setIsIndexable(true);
//
//        EarningTypeResponseDto responseDto = new EarningTypeResponseDto();
//        responseDto.setId(earningTypeId);
//        responseDto.setName("Updated Name");
//        responseDto.setCode("UPDATED");
//        responseDto.setDescription("updated description");
//        responseDto.setIncludeInAverageSalaryCalc(true);
//        responseDto.setIncludeInFot(true);
//        responseDto.setIsIndexable(true);
//
//        Mockito
//                .when(typeMapper.fromUpdate(Mockito.any(EarningTypeUpdateDto.class)))
//                .thenReturn(type);
//
//        Mockito
//                .when(earningTypeService.update(Mockito.eq(earningTypeId), Mockito.any(EarningType.class)))
//                .thenReturn(savedType);
//
//        Mockito
//                .when(typeMapper.toResponse(Mockito.any(EarningType.class)))
//                .thenReturn(responseDto);
//
//        String json = objectMapper.writeValueAsString(dto);
//
//        mockMvc.perform(put("/api/earning-types/" + earningTypeId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(earningTypeId));
//    }
//}