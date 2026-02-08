package kz.zzhalelov.staffflow.server.organization;

import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {
    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    OrganizationServiceImpl organizationService;

    @Test
    void create_shouldCreate() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setShortName("Test");
        organization.setFullName("Test Inc.");
        organization.setAddress("Test address");
        organization.setIsBranch(false);
        organization.setHasBranches(false);
        organization.setIdNumber("123456789012");
        organization.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Mockito
                .when(organizationRepository.save(Mockito.any(Organization.class)))
                .thenReturn(organization);

        Organization savedOrganization = organizationService.create(organization);

        assertEquals(organization.getId(), savedOrganization.getId());
        assertEquals(organization.getShortName(), savedOrganization.getShortName());
        assertEquals(organization.getFullName(), savedOrganization.getFullName());
        assertEquals(organization.getAddress(), savedOrganization.getAddress());
        assertEquals(organization.getIsBranch(), savedOrganization.getIsBranch());
        assertEquals(organization.getHasBranches(), savedOrganization.getHasBranches());
        assertEquals(organization.getIdNumber(), savedOrganization.getIdNumber());
        assertEquals(organization.getOrganizationType(), savedOrganization.getOrganizationType());
    }

    @Test
    void create_shouldThrow_whenIdNumberIsNull() {
        Organization organization = new Organization();
        organization.setIdNumber(null);

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> organizationService.create(organization)
        );
        assertEquals("ИИН должен быть заполнен и составлять 12 знаков", ex.getMessage());
    }

    @Test
    void create_shouldThrow_whenIdNumberIsBlank() {
        Organization organization = new Organization();
        organization.setIdNumber("   ");

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> organizationService.create(organization)
        );
        assertEquals("ИИН должен быть заполнен и составлять 12 знаков", ex.getMessage());
    }

    @Test
    void create_shouldThrow_whenIdNumberLengthNotCorrect() {
        Organization organization = new Organization();
        organization.setIdNumber("123");

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> organizationService.create(organization)
        );
        assertEquals("ИИН должен быть заполнен и составлять 12 знаков", ex.getMessage());
    }

    @Test
    void findAll_shouldReturnList() {
        Organization organization1 = new Organization();
        organization1.setId(1L);
        organization1.setShortName("Test");
        organization1.setFullName("Test Inc.");
        organization1.setAddress("Test address");
        organization1.setIsBranch(false);
        organization1.setHasBranches(false);
        organization1.setIdNumber("123456789012");
        organization1.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Organization organization2 = new Organization();
        organization2.setId(2L);
        organization2.setShortName("Test");
        organization2.setFullName("Test Inc.");
        organization2.setAddress("Test address");
        organization2.setIsBranch(false);
        organization2.setHasBranches(false);
        organization2.setIdNumber("123456789012");
        organization2.setOrganizationType(OrganizationType.LEGAL_PERSON);

        List<Organization> existingOrganizations = List.of(organization1, organization2);

        Mockito
                .when(organizationRepository.findAll())
                .thenReturn(existingOrganizations);

        List<Organization> savedOrganizations = organizationService.findAll();
        assertEquals(2, savedOrganizations.size());
        assertEquals(organization1.getId(), savedOrganizations.get(0).getId());
        assertEquals(organization2.getId(), savedOrganizations.get(1).getId());
    }

    @Test
    void findById() {
        Organization existingOrganization = new Organization();
        existingOrganization.setId(1L);
        existingOrganization.setShortName("Test");
        existingOrganization.setFullName("Test Inc.");
        existingOrganization.setAddress("Test address");
        existingOrganization.setIsBranch(false);
        existingOrganization.setHasBranches(false);
        existingOrganization.setIdNumber("123456789012");
        existingOrganization.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Mockito
                .when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(existingOrganization));

        Organization savedOrganization = organizationService.findById(1L);

        assertEquals(existingOrganization.getId(), savedOrganization.getId());
        assertEquals(existingOrganization.getShortName(), savedOrganization.getShortName());
        assertEquals(existingOrganization.getFullName(), savedOrganization.getFullName());
        assertEquals(existingOrganization.getAddress(), savedOrganization.getAddress());
        assertEquals(existingOrganization.getIsBranch(), savedOrganization.getIsBranch());
        assertEquals(existingOrganization.getHasBranches(), savedOrganization.getHasBranches());
        assertEquals(existingOrganization.getIdNumber(), savedOrganization.getIdNumber());
        assertEquals(existingOrganization.getOrganizationType(), savedOrganization.getOrganizationType());
    }

    @Test
    void findById_shouldThrow_whenNoExists() {
        Mockito
                .when(organizationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> organizationService.findById(1L));
        Mockito
                .verify(organizationRepository)
                .findById(1L);
    }

    @Test
    void update_shouldSaveOrganization() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setShortName("Test");
        organization.setFullName("Test Inc.");
        organization.setAddress("Beverly Hills");
        organization.setIsBranch(false);
        organization.setHasBranches(false);
        organization.setIdNumber("123456789012");
        organization.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Mockito
                .when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(organization));

        Mockito
                .when(organizationRepository.save(Mockito.any(Organization.class)))
                .thenReturn(organization);

        Organization savedOrganization = organizationService.update(1L, organization);
        assertEquals(organization.getId(), savedOrganization.getId());
        assertEquals(organization.getShortName(), savedOrganization.getShortName());
        assertEquals(organization.getFullName(), savedOrganization.getFullName());
        assertEquals(organization.getAddress(), savedOrganization.getAddress());
        assertEquals(organization.getIsBranch(), savedOrganization.getIsBranch());
        assertEquals(organization.getHasBranches(), savedOrganization.getHasBranches());
        assertEquals(organization.getIdNumber(), savedOrganization.getIdNumber());
        assertEquals(organization.getOrganizationType(), savedOrganization.getOrganizationType());
    }

    @Test
    void update_shouldOnlyUpdateNonNullFields() {
        long orgId = 1L;

        Organization existing = new Organization();
        existing.setId(orgId);
        existing.setAddress("Beverly Hills");
        existing.setFullName("Umbrella Corporation");
        existing.setShortName("Umbrella Corp");
        existing.setHasBranches(false);
        existing.setIsBranch(false);
        existing.setIdNumber("123456789012");
        existing.setOrganizationType(OrganizationType.LEGAL_PERSON);

        //Nothing changed. All fields are null
        Organization updated = new Organization();

        Mockito
                .when(organizationRepository.findById(orgId))
                .thenReturn(Optional.of(existing));

        Mockito
                .when(organizationRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        Organization result = organizationService.update(orgId, updated);
        assertEquals("Beverly Hills", result.getAddress());
        assertEquals("Umbrella Corporation", result.getFullName());
        assertEquals("Umbrella Corp", result.getShortName());
        assertEquals(false, result.getHasBranches());
        assertEquals(false, result.getIsBranch());
        assertEquals("123456789012", result.getIdNumber());
        assertEquals(OrganizationType.LEGAL_PERSON, result.getOrganizationType());
    }

    @Test
    void update_shouldNotUpdateBlankFields() {
        long orgId = 1L;

        Organization existing = new Organization();
        existing.setId(orgId);
        existing.setAddress("Beverly Hills");
        existing.setFullName("Umbrella Corporation");
        existing.setShortName("Umbrella Corp");
        existing.setHasBranches(false);
        existing.setIsBranch(false);
        existing.setIdNumber("123456789012");
        existing.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Organization updated = new Organization();
        updated.setIdNumber("");
        updated.setShortName("");
        updated.setFullName("");
        updated.setAddress("");

        Mockito
                .when(organizationRepository.findById(orgId))
                .thenReturn(Optional.of(existing));

        Mockito
                .when(organizationRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0));

        Organization result = organizationService.update(orgId, updated);

        assertEquals("Beverly Hills", result.getAddress());
        assertEquals("Umbrella Corporation", result.getFullName());
        assertEquals("Umbrella Corp", result.getShortName());
        assertEquals("123456789012", result.getIdNumber());
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setShortName("Test");
        organization.setFullName("Test Inc.");
        organization.setAddress("Beverly Hills");
        organization.setIsBranch(false);
        organization.setHasBranches(false);
        organization.setIdNumber("123456789012");
        organization.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Mockito
                .when(organizationRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> organizationService.update(1L, organization));
        assertEquals("Organization not found", ex.getMessage());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        Mockito
                .when(organizationRepository.existsById(1L))
                .thenReturn(true);

        organizationService.delete(1L);

        Mockito
                .verify(organizationRepository).deleteById(1L);
    }

    @Test
    void delete_shouldDelete_whenNotExists() {
        Mockito
                .when(organizationRepository.existsById(1L))
                .thenReturn(false);

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> organizationService.delete(1L)
        );

        assertEquals("Организация не найдена", ex.getMessage());
    }

    @Test
    void findByIdNumber() {
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setShortName("Test");
        organization.setFullName("Test Inc.");
        organization.setAddress("Test address");
        organization.setIsBranch(false);
        organization.setHasBranches(false);
        organization.setIdNumber("123456789012");
        organization.setOrganizationType(OrganizationType.LEGAL_PERSON);

        Mockito
                .when(organizationRepository.findByIdNumber("123456789012"))
                .thenReturn(Optional.of(organization));

        Organization result = organizationService.findByIdNumber("123456789012");
        assertEquals("123456789012", result.getIdNumber());

        Mockito
                .verify(organizationRepository).findByIdNumber("123456789012");
    }
}