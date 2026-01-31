//package kz.zzhalelov.staffflow.server.organization;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kz.zzhalelov.staffflow.server.config.NoSecurityTestConfig;
//import kz.zzhalelov.staffflow.server.config.SecurityConfig;
//import kz.zzhalelov.staffflow.server.employee.EmployeeController;
//import kz.zzhalelov.staffflow.server.organization.dto.OrganizationCreateDto;
//import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
//import kz.zzhalelov.staffflow.server.organization.dto.OrganizationUpdateDto;
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
//import java.util.Optional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(
//        controllers = OrganizationController.class,
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                classes = JwtAuthenticationFilter.class
//        )
//)
//@Import(NoSecurityTestConfig.class)
//@AutoConfigureMockMvc
//class OrganizationControllerTest {
//    @MockitoBean
//    OrganizationService organizationService;
//    @MockitoBean
//    OrganizationRepository organizationRepository;
//    @MockitoBean
//    OrganizationMapper organizationMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @SneakyThrows
//    void findAll_shouldReturnNonEmptyList() {
//        Organization organization1 = new Organization();
//        organization1.setId(1L);
//        organization1.setShortName("Test");
//        organization1.setFullName("Test Inc.");
//        organization1.setAddress("Test address");
//        organization1.setIsBranch(false);
//        organization1.setHasBranches(false);
//        organization1.setIdNumber("123456789012");
//        organization1.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Organization organization2 = new Organization();
//        organization2.setId(2L);
//        organization2.setShortName("Test");
//        organization2.setFullName("Test Inc.");
//        organization2.setAddress("Test address");
//        organization2.setIsBranch(false);
//        organization2.setHasBranches(false);
//        organization2.setIdNumber("123456789012");
//        organization2.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Mockito
//                .when(organizationService.findAll())
//                .thenReturn(List.of(organization1, organization2));
//
//        mockMvc.perform(get("/api/organizations"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(organization1.getId()))
//                .andExpect(jsonPath("$[1].id").value(organization2.getId()));
//    }
//
//    @Test
//    @SneakyThrows
//    void findById() {
//        long orgId = 1;
//
//        Organization organization1 = new Organization();
//        organization1.setId(1L);
//        organization1.setShortName("Test");
//        organization1.setFullName("Test Inc.");
//        organization1.setAddress("Test address");
//        organization1.setIsBranch(false);
//        organization1.setHasBranches(false);
//        organization1.setIdNumber("123456789012");
//        organization1.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Mockito
//                .when(organizationService.findById(Mockito.anyLong()))
//                .thenReturn(organization1);
//
//        mockMvc.perform(get("/api/organizations/" + orgId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(orgId));
//    }
//
//    @Test
//    @SneakyThrows
//    void create_shouldReturnCreated() {
//        long orgId = 1;
//
//        OrganizationCreateDto createDto = new OrganizationCreateDto();
//        createDto.setShortName("Test");
//        createDto.setFullName("Test Inc.");
//        createDto.setAddress("Test address");
//        createDto.setIsBranch(false);
//        createDto.setHasBranches(false);
//        createDto.setIdNumber("123456789012");
//        createDto.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Mockito
//                .when(organizationService.create(Mockito.any(Organization.class)))
//                .thenAnswer(i -> {
//                    Organization argument = i.getArgument(0, Organization.class);
//                    argument.setId(orgId);
//                    return argument;
//                });
//
//        String json = objectMapper.writeValueAsString(createDto);
//
//        mockMvc.perform(post("/api/organizations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(orgId));
//    }
//
//    @Test
//    @SneakyThrows
//    void update_shouldReturnOk() {
//        long orgId = 1L;
//
//        OrganizationUpdateDto updateDto = new OrganizationUpdateDto();
//        updateDto.setShortName("Test");
//        updateDto.setFullName("Test Inc.");
//        updateDto.setAddress("Test address");
//        updateDto.setIsBranch(false);
//        updateDto.setHasBranches(false);
//        updateDto.setIdNumber("123456789012");
//        updateDto.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Organization organization = new Organization();
//        organization.setId(orgId);
//        organization.setShortName("test");
//
//        Mockito
//                .when(organizationRepository.findById(Mockito.eq(orgId)))
//                .thenReturn(Optional.of(new Organization()));
//
//        Mockito
//                .when(organizationService.update(Mockito.anyLong(), Mockito.any(Organization.class)))
//                .thenReturn(organization);
//
//        String json = objectMapper.writeValueAsString(updateDto);
//
//        mockMvc.perform(patch("/api/organizations/" + orgId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(organization.getId()));
//    }
//
//    @Test
//    @SneakyThrows
//    void deleteById() {
//        long orgId = 1L;
//
//        mockMvc.perform(delete("/api/organizations/" + orgId))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @SneakyThrows
//    void findByIdNumber() {
//        String idNumber = "123456789012";
//
//        Organization organization = new Organization();
//        organization.setId(1L);
//        organization.setShortName("Test");
//        organization.setFullName("Test Inc.");
//        organization.setAddress("Test address");
//        organization.setIsBranch(false);
//        organization.setHasBranches(false);
//        organization.setIdNumber("123456789012");
//        organization.setOrganizationType(OrganizationType.LEGAL_PERSON);
//
//        Mockito
//                .when(organizationService.findByIdNumber(Mockito.anyString()))
//                .thenReturn(organization);
//
//        mockMvc.perform(get("/api/organizations/bin/" + idNumber))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(organization.getId()))
//                .andExpect(jsonPath("$.idNumber").value(organization.getIdNumber()));
//    }
//}