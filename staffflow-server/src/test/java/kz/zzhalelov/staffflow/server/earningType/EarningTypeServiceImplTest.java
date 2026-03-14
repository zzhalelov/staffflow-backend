package kz.zzhalelov.staffflow.server.earningType;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EarningTypeServiceImplTest {
    @Mock
    private EarningTypeRepository earningTypeRepository;
    @Mock
    private EarningTypeHistoryRepository earningTypeHistoryRepository;
    @InjectMocks
    EarningTypeServiceImpl earningTypeService;

    @Test
    void create_shouldCreate() {
        EarningType earningType = new EarningType();
        earningType.setId(1L);
        earningType.setCode("SALARY_BY_DAYS");
        earningType.setName("Оклад по дням");
        earningType.setDescription("Оклад по дням");
        earningType.setIncludeInAverageSalaryCalc(true);
        earningType.setIncludeInFot(true);
        earningType.setIndexable(true);

        Mockito
                .when(earningTypeRepository.save(any(EarningType.class)))
                .thenReturn(earningType);

        EarningType savedEarningType = earningTypeService.create(earningType);

        assertEquals(earningType.getId(), savedEarningType.getId());
        assertEquals(earningType.getCode(), savedEarningType.getCode());
        assertEquals(earningType.getName(), savedEarningType.getName());
        assertEquals(earningType.getDescription(), savedEarningType.getDescription());
        assertEquals(earningType.getIncludeInAverageSalaryCalc(), savedEarningType.includeInAverageSalaryCalc);
        assertEquals(earningType.getIncludeInFot(), savedEarningType.getIncludeInFot());
        assertEquals(earningType.getIndexable(), savedEarningType.getIndexable());
    }

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        EarningType existing = new EarningType();
        existing.setName("Оклад по дням");

        EarningType updated = new EarningType();
        updated.setName("Оклад по часам");

        Mockito
                .when(earningTypeRepository.existsByNameIgnoreCase("Оклад по часам"))
                .thenReturn(true);

        assertThrows(ConflictException.class, () ->
                earningTypeService.create(updated));
    }

    @Test
    void create_shouldThrow_whenCodeAlreadyExists() {
        EarningType existing = new EarningType();
        existing.setName("Оклад по дням");
        existing.setCode("ОКЛДН");

        EarningType updated = new EarningType();
        updated.setName("Оклад по дням");
        updated.setCode("ОКЛДН");

        Mockito
                .when(earningTypeRepository.existsByCodeIgnoreCase("ОКЛДН"))
                .thenReturn(true);

        assertThrows(ConflictException.class, () ->
                earningTypeService.create(updated));
    }

    @Test
    void update_shouldThrow_whenNotExists() {
        long id = 1L;
        EarningType updated = new EarningType();
        updated.setCode("SALARY_BY_DAYS");
        updated.setName("Оклад по дням");
        updated.setDescription("Оклад по дням");
        updated.setIncludeInAverageSalaryCalc(true);
        updated.setIncludeInFot(true);
        updated.setIndexable(true);

        Mockito
                .when(earningTypeRepository.existsByNameIgnoreCase("Оклад по дням"))
                .thenReturn(false);

        Mockito
                .when(earningTypeRepository.existsByCodeIgnoreCase("SALARY_BY_DAYS"))
                .thenReturn(false);

        Mockito
                .when(earningTypeRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> earningTypeService.update(id, updated));
        assertEquals("Earning Type not found", ex.getMessage());

        Mockito.verify(earningTypeRepository, Mockito.never()).save(any());
    }

    @Test
    void update_shouldUpdateAndSaveEntity() {
        EarningType existing = new EarningType();
        existing.setId(1L);
        existing.setName("Оклад по дням");
        existing.setCode("ОКЛДН");
        existing.setIncludeInFot(true);
        existing.setIncludeInAverageSalaryCalc(true);
        existing.setIndexable(true);
        existing.setDescription("Месячный оклад по дням");

        EarningType updated = new EarningType();
        updated.setName("Оклад по часам");
        updated.setCode("ОКЛЧ");
        updated.setIncludeInFot(true);
        updated.setIncludeInAverageSalaryCalc(true);
        updated.setIndexable(true);
        updated.setDescription("Оклад по часам");

        Mockito
                .when(earningTypeRepository.existsByNameIgnoreCase("Оклад по часам"))
                .thenReturn(false);

        Mockito
                .when(earningTypeRepository.existsByCodeIgnoreCase("ОКЛЧ"))
                .thenReturn(false);

        Mockito
                .when(earningTypeRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.of(existing));

        Mockito
                .when(earningTypeRepository.save(any(EarningType.class)))
                .thenAnswer(i -> i.getArgument(0));

        EarningType result = earningTypeService.update(1L, updated);

        assertEquals("Оклад по часам", result.getName());
        assertEquals("ОКЛЧ", result.getCode());
        assertEquals("Оклад по часам", result.getDescription());
        Mockito.verify(earningTypeRepository).save(existing);
    }

    @Test
    void update_shouldThrow_whenNameExists() {
        long id = 1L;
        EarningType updated = new EarningType();
        updated.setId(id);
        updated.setCode("SALARY_BY_DAYS");
        updated.setName("Оклад по дням");
        updated.setDescription("Оклад по дням");
        updated.setIncludeInAverageSalaryCalc(true);
        updated.setIncludeInFot(true);
        updated.setIndexable(true);

        Mockito
                .when(earningTypeRepository.existsByNameIgnoreCase("Оклад по дням"))
                .thenReturn(true);

        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> earningTypeService.update(id, updated)
        );

        assertEquals("Earning type with this name already exists: Оклад по дням", ex.getMessage());

        Mockito.verify(earningTypeRepository).existsByNameIgnoreCase("Оклад по дням");
        Mockito.verify(earningTypeRepository, Mockito.never()).findByIdAndDeletedFalse(anyLong());
        Mockito.verify(earningTypeRepository, Mockito.never()).save(any(EarningType.class));
    }

    @Test
    void update_shouldThrow_whenCodeExists() {
        long id = 1L;
        EarningType updated = new EarningType();
        updated.setId(id);
        updated.setCode("SALARY_BY_DAYS");
        updated.setName("Оклад по дням");
        updated.setDescription("Оклад по дням");
        updated.setIncludeInAverageSalaryCalc(true);
        updated.setIncludeInFot(true);
        updated.setIndexable(true);

        Mockito
                .when(earningTypeRepository.existsByCodeIgnoreCase("SALARY_BY_DAYS"))
                .thenReturn(true);

        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> earningTypeService.update(id, updated)
        );

        assertEquals("Earning type with this code already exists: SALARY_BY_DAYS", ex.getMessage());

        Mockito.verify(earningTypeRepository).existsByNameIgnoreCase("Оклад по дням");
        Mockito.verify(earningTypeRepository, Mockito.never()).findByIdAndDeletedFalse(anyLong());
        Mockito.verify(earningTypeRepository, Mockito.never()).save(any(EarningType.class));
    }

    @Test
    void findAll_shouldReturnList() {
        Pageable pageable = PageRequest.of(0, 10);

        EarningType earningType1 = new EarningType();
        earningType1.setId(1L);
        earningType1.setCode("SALARY_BY_DAYS");
        earningType1.setName("Оклад по дням");
        earningType1.setDescription("Оклад по дням");
        earningType1.setIncludeInAverageSalaryCalc(true);
        earningType1.setIncludeInFot(true);
        earningType1.setIndexable(true);

        EarningType earningType2 = new EarningType();
        earningType2.setId(2L);
        earningType2.setCode("BONUS");
        earningType2.setName("Премия");
        earningType2.setDescription("Премия");
        earningType2.setIncludeInAverageSalaryCalc(true);
        earningType2.setIncludeInFot(true);
        earningType2.setIndexable(true);

        Page<EarningType> page = new PageImpl<>(List.of(earningType1, earningType2));

        Mockito
                .when(earningTypeRepository.findAllByDeletedFalse(pageable))
                .thenReturn(page);

        Page<EarningType> savedEarningTypes = earningTypeService.findAll(pageable);
        assertEquals(2, savedEarningTypes.getContent().size());
        assertEquals(1, savedEarningTypes.getTotalPages());
        assertEquals(2, savedEarningTypes.getTotalElements());
        assertEquals(earningType1.getId(), savedEarningTypes.getContent().get(0).getId());
        assertEquals(earningType2.getId(), savedEarningTypes.getContent().get(1).getId());
    }

    @Test
    void findById() {
        EarningType earningType = new EarningType();
        earningType.setId(1L);
        earningType.setCode("SALARY_BY_DAYS");
        earningType.setName("Оклад по дням");
        earningType.setDescription("Оклад по дням");
        earningType.setIncludeInAverageSalaryCalc(true);
        earningType.setIncludeInFot(true);
        earningType.setIndexable(true);

        Mockito
                .when(earningTypeRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.of(earningType));

        EarningType savedEarningType = earningTypeService.findById(1L);

        assertEquals(earningType.getId(), savedEarningType.getId());
        assertEquals(earningType.getCode(), savedEarningType.getCode());
        assertEquals(earningType.getName(), savedEarningType.getName());
        assertEquals(earningType.getIndexable(), savedEarningType.getIndexable());
        assertEquals(earningType.getIncludeInFot(), savedEarningType.getIncludeInFot());
        assertEquals(earningType.getIncludeInAverageSalaryCalc(), savedEarningType.getIncludeInAverageSalaryCalc());
        assertEquals(earningType.getDescription(), savedEarningType.getDescription());
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        Mockito
                .when(earningTypeRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> earningTypeService.findById(1L)
        );
        Mockito
                .verify(earningTypeRepository).findByIdAndDeletedFalse(1L);
    }

    @Test
    void delete_shouldThrow_whenNotExists() {
        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> earningTypeService.delete(1L)
        );

        assertEquals("Earning type not found", ex.getMessage());
        Mockito
                .verify(earningTypeRepository, Mockito.never())
                .deleteById(Mockito.anyLong());
    }

    @Test
    void delete_shouldMarkAsDeleted_withUsername_whenAuthenticated() {
        long id = 1L;
        EarningType earningType = new EarningType();
        earningType.setId(id);
        String mockUsername = "active_admin_ivanov"; // Используем уникальное имя

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(earningType));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(mockUsername);

        try {
            earningTypeService.delete(id);
            assertTrue(earningType.isDeleted(), "Флаг deleted должен быть true");
            assertEquals(mockUsername, earningType.getDeletedBy(), "Имя удалившего должно быть взято из SecurityContext");
            assertNotNull(earningType.getDeletedAt(), "Дата удаления должна быть проставлена");
            Mockito.verify(earningTypeRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldMarkAsDeleted_withSystem_whenUserIsAnonymous() {
        long id = 1L;
        EarningType earningType = new EarningType();
        earningType.setId(id);

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(earningType));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken anonymousToken = mock(AnonymousAuthenticationToken.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(anonymousToken);

        try {
            earningTypeService.delete(id);
            assertTrue(earningType.isDeleted());
            assertEquals("system", earningType.getDeletedBy(), "Для анонимного входа должен использоваться 'system'");
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldUseSystem_whenAuthIsNull() {
        long id = 1L;
        EarningType earningType = new EarningType();
        earningType.setId(id);

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(earningType));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        try {
            earningTypeService.delete(id);
            assertTrue(earningType.isDeleted());
            assertEquals("system", earningType.getDeletedBy());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldUseSystem_whenAnonymousToken() {
        long id = 1L;
        EarningType earningType = new EarningType();
        earningType.setId(id);

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(earningType));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken auth = mock(AnonymousAuthenticationToken.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        try {
            earningTypeService.delete(id);
            assertEquals("system", earningType.getDeletedBy());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldThrowNotFound_whenEarningTypeDoesNotExist() {
        long id = 1L;
        when(earningTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> earningTypeService.restore(id)
        );

        assertEquals("Earning type not found", ex.getMessage());

        Mockito.verify(earningTypeRepository).findById(id);
        Mockito.verifyNoMoreInteractions(earningTypeRepository);
    }

    @Test
    void restore_shouldThrowConflict_whenEarningTypeIsNotInDeletedState() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(type));

        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> earningTypeService.restore(id)
        );

        assertEquals("Earning type not deleted", ex.getMessage());
        Mockito.verify(earningTypeRepository).findById(id);
        assertNull(type.getDeletedAt());
    }

    @Test
    void restore_shouldRestoreWithActualUsername_whenAuthenticated() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);
        type.markAsDeleted("system");

        String mockUsername = "system";

        when(earningTypeRepository.findById(id))
                .thenReturn(Optional.of(type));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(mockUsername);

        try {
            earningTypeService.restore(id);
            assertFalse(type.isDeleted(), "Флаг deleted должен стать false после restore");
            Mockito.verify(earningTypeRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldRestoreWithSystemUsername_whenNotAuthenticated() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);
        type.markAsDeleted("system");

        when(earningTypeRepository.findById(id))
                .thenReturn(Optional.of(type));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        try {
            earningTypeService.restore(id);
            assertFalse(type.isDeleted());
            Mockito.verify(earningTypeRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldThrowNotFound_whenIdNotExists() {
        long id = 999L;
        when(earningTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> earningTypeService.restore(id));
    }

    @Test
    void restore_shouldThrowConflict_whenIsNotDeleted() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);
        type.restore("system");

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(type));

        assertThrows(ConflictException.class, () -> earningTypeService.restore(id));
    }

    @Test
    void restore_shouldUseSystem_whenAnonymousToken() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);
        type.markAsDeleted("old-user");

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(type));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken anonymousToken = mock(AnonymousAuthenticationToken.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(anonymousToken);

        try {
            earningTypeService.restore(id);
            assertFalse(type.isDeleted());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldUseSystem_whenAuthenticationIsNotAuthenticated() {
        long id = 1L;
        EarningType type = new EarningType();
        type.setId(id);
        type.markAsDeleted("old-user");

        when(earningTypeRepository.findById(id)).thenReturn(Optional.of(type));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(false);

        try {
            earningTypeService.restore(id);
            assertFalse(type.isDeleted());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void findHistoryByEarningTypeId_shouldReturnList() {
        Long earningTypeId = 1L;

        EarningTypeHistory history1 = new EarningTypeHistory();
        history1.setId(101L);

        EarningTypeHistory history2 = new EarningTypeHistory();
        history2.setId(102L);

        List<EarningTypeHistory> mockHistory = List.of(history1, history2);

        when(earningTypeHistoryRepository.findAllByEarningTypeIdOrderByStartDateDesc(earningTypeId))
                .thenReturn(mockHistory);

        List<EarningTypeHistory> result = earningTypeService.findHistoryByEarningTypeId(earningTypeId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getId());
        assertEquals(102L, result.get(1).getId());

        verify(earningTypeHistoryRepository).findAllByEarningTypeIdOrderByStartDateDesc(earningTypeId);
    }
}