//package kz.zzhalelov.staffflow.server.position;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kz.zzhalelov.staffflow.server.config.NoSecurityTestConfig;
//import kz.zzhalelov.staffflow.server.config.SecurityConfig;
//import kz.zzhalelov.staffflow.server.employee.EmployeeController;
//import kz.zzhalelov.staffflow.server.organization.OrganizationController;
//import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
//import kz.zzhalelov.staffflow.server.position.dto.*;
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
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(
//        controllers = PositionController.class,
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                classes = JwtAuthenticationFilter.class
//        )
//)
//@Import(NoSecurityTestConfig.class)
//@AutoConfigureMockMvc
//class PositionControllerTest {
//    @MockitoBean
//    PositionRepository positionRepository;
//    @MockitoBean
//    PositionService positionService;
//    @MockitoBean
//    PositionMapper positionMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @SneakyThrows
//    void create() {
//        long positionTypeId = 1;
//
//        PositionCreateDto createDto = new PositionCreateDto();
//        createDto.setName("Test");
//
//        Position position = new Position();
//        position.setName("Test");
//
//        PositionFullResponseDto responseDto = new PositionFullResponseDto();
//        responseDto.setId(positionTypeId);
//        responseDto.setName("Test");
//
//        Mockito
//                .when(positionMapper.fromCreate(Mockito.any()))
//                .thenReturn(position);
//
//        Mockito
//                .when(positionService.createPosition(Mockito.any(Position.class)))
//                .thenAnswer(i -> {
//                    Position argument = i.getArgument(0, Position.class);
//                    argument.setId(positionTypeId);
//                    return argument;
//                });
//
//        Mockito
//                .when(positionMapper.toFullResponse(Mockito.any()))
//                .thenReturn(responseDto);
//
//        String json = objectMapper.writeValueAsString(createDto);
//
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(positionTypeId));
//    }
//
//    @Test
//    @SneakyThrows
//    void findAll_shouldReturnNonEmptyList() {
//        Position position1 = new Position();
//        position1.setId(1L);
//        position1.setName("Position 1");
//
//        Position position2 = new Position();
//        position2.setId(2L);
//        position2.setName("Position 2");
//
//        PositionFullResponseDto dto1 = new PositionFullResponseDto();
//        dto1.setId(1L);
//        dto1.setName("Position 1");
//
//        PositionFullResponseDto dto2 = new PositionFullResponseDto();
//        dto2.setId(2L);
//        dto2.setName("Position 2");
//
//        Mockito
//                .when(positionService.findAll())
//                .thenReturn(List.of(position1, position2));
//
//        Mockito
//                .when(positionMapper.toFullResponse(position1))
//                .thenReturn(dto1);
//
//        Mockito
//                .when(positionMapper.toFullResponse(position2))
//                .thenReturn(dto2);
//
//        mockMvc.perform(get("/api/positions"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(position1.getId()))
//                .andExpect(jsonPath("$[1].id").value(position2.getId()));
//    }
//
//    @Test
//    @SneakyThrows
//    void findById() {
//        long positionId = 1;
//
//        Position position = new Position();
//        position.setId(1L);
//        position.setName("Position 1");
//
//        PositionFullResponseDto responseDto = new PositionFullResponseDto();
//        responseDto.setId(1L);
//        responseDto.setName("Position 1");
//
//        Mockito
//                .when(positionService.findById(Mockito.anyLong()))
//                .thenReturn(position);
//
//        Mockito
//                .when(positionMapper.toFullResponse(position))
//                .thenReturn(responseDto);
//
//        mockMvc.perform(get("/api/positions/" + positionId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(positionId));
//    }
//
////    @Test
////    @SneakyThrows
////    void update() {
////        long positionTypeId = 1L;
////
////        PositionUpdateDto dto = new PositionUpdateDto();
////        dto.setName("Updated Name");
////
////        Position position = new Position();
////        position.setName("Updated Name");
////
////        Position savedPosition = new Position();
////        savedPosition.setName("Updated Name");
////
////        PositionResponseDto responseDto = new PositionResponseDto();
////        responseDto.setId(positionTypeId);
////        responseDto.setName("Updated Name");
////
////        Mockito
////                .when(positionMapper.fromUpdate(Mockito.any(PositionUpdateDto.class)))
////                .thenReturn(position);
////
////        Mockito
////                .when(positionService.update(Mockito.eq(positionTypeId), Mockito.any(Position.class)))
////                .thenReturn(savedPosition);
////
////        Mockito
////                .when(positionMapper.toResponse(Mockito.any(Position.class)))
////                .thenReturn(responseDto);
////
////        String json = objectMapper.writeValueAsString(dto);
////
////        mockMvc.perform(patch("/api/positions/" + positionTypeId)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(json))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").value(positionTypeId));
////    }
//
//    @Test
//    @SneakyThrows
//    void delete_Success() {
//        long positionTypeId = 1;
//
//        Mockito
//                .doNothing()
//                .when(positionService)
//                .delete(positionTypeId);
//
//        mockMvc.perform(delete("/api/positions/" + positionTypeId))
//                .andExpect(status().isNoContent());
//
//        Mockito
//                .verify(positionService)
//                .delete(positionTypeId);
//    }
//}