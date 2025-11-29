package kz.zzhalelov.staffflow.server.earningType;

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
import static org.mockito.ArgumentMatchers.any;

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
        earningType.setIsIndexable(true);

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
        assertEquals(earningType.getIsIndexable(), savedEarningType.getIsIndexable());
    }

    @Test
    void update_shouldThrow_whenNotExists() {
        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> earningTypeService.update(1L, new EarningType())
        );

        assertEquals("EarningType not found", ex.getMessage());

        Mockito
                .verify(earningTypeRepository)
                .findById(1L);
        Mockito
                .verifyNoMoreInteractions(earningTypeHistoryRepository);
    }

    @Test
    void update_shouldCloseLastHistory_whenHistoryExists() {
        EarningType existing = new EarningType();
        existing.setId(1L);

        EarningTypeHistory lastHistory = new EarningTypeHistory();
        lastHistory.setId(10L);

        EarningType updated = new EarningType();
        updated.setName("Updated");
        updated.setCode("NEW");
        updated.setDescription("Updated desc");

        Mockito.when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(earningTypeHistoryRepository
                        .findTopByEarningTypeOrderByStartDateDesc(existing))
                .thenReturn(Optional.of(lastHistory));

        Mockito.when(earningTypeRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EarningType result = earningTypeService.update(1L, updated);

        assertEquals("Updated", result.getName());
        assertEquals("NEW", result.getCode());

        // проверка, что история была закрыта
        assertNotNull(lastHistory.getEndDate());

        Mockito.verify(earningTypeHistoryRepository).save(lastHistory);
    }

    @Test
    void update_shouldNotTryCloseHistory_whenNoHistory() {
        EarningType existing = new EarningType();
        existing.setId(1L);

        EarningType updated = new EarningType();
        updated.setName("Updated");

        Mockito.when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(earningTypeHistoryRepository
                        .findTopByEarningTypeOrderByStartDateDesc(existing))
                .thenReturn(Optional.empty());

        earningTypeService.update(1L, updated);

        // история не закрывалась, но создавалась новая — 1 save()
        Mockito.verify(earningTypeHistoryRepository, Mockito.times(1))
                .save(any(EarningTypeHistory.class));
    }

    @Test
    void update_shouldUpdateAndSaveEntity() {
        EarningType existing = new EarningType();
        existing.setId(1L);

        EarningType updated = new EarningType();
        updated.setName("NewName");
        updated.setCode("X100");
        updated.setDescription("New Desc");

        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        Mockito.when(earningTypeHistoryRepository
                        .findTopByEarningTypeOrderByStartDateDesc(existing))
                .thenReturn(Optional.empty());

        earningTypeService.update(1L, updated);

        assertEquals("NewName", existing.getName());
        assertEquals("X100", existing.getCode());
        assertEquals("New Desc", existing.getDescription());

        Mockito.verify(earningTypeRepository).save(existing);
    }

    @Test
    void findAll_shouldReturnList() {
        EarningType earningType1 = new EarningType();
        earningType1.setId(1L);
        earningType1.setCode("SALARY_BY_DAYS");
        earningType1.setName("Оклад по дням");
        earningType1.setDescription("Оклад по дням");
        earningType1.setIncludeInAverageSalaryCalc(true);
        earningType1.setIncludeInFot(true);
        earningType1.setIsIndexable(true);

        EarningType earningType2 = new EarningType();
        earningType2.setId(2L);
        earningType2.setCode("BONUS");
        earningType2.setName("Премия");
        earningType2.setDescription("Премия");
        earningType2.setIncludeInAverageSalaryCalc(true);
        earningType2.setIncludeInFot(true);
        earningType2.setIsIndexable(true);

        List<EarningType> existingEarningTypes = List.of(earningType1, earningType2);

        Mockito
                .when(earningTypeRepository.findAll())
                .thenReturn(existingEarningTypes);
        List<EarningType> savedEarningTypes = earningTypeService.findAll();
        assertEquals(2, savedEarningTypes.size());
        assertEquals(earningType1.getId(), savedEarningTypes.get(0).getId());
        assertEquals(earningType2.getId(), savedEarningTypes.get(1).getId());
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
        earningType.setIsIndexable(true);

        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.of(earningType));

        EarningType savedEarningType = earningTypeService.findById(1L);

        assertEquals(earningType.getId(), savedEarningType.getId());
        assertEquals(earningType.getCode(), savedEarningType.getCode());
        assertEquals(earningType.getName(), savedEarningType.getName());
        assertEquals(earningType.getIsIndexable(), savedEarningType.getIsIndexable());
        assertEquals(earningType.getIncludeInFot(), savedEarningType.getIncludeInFot());
        assertEquals(earningType.getIncludeInAverageSalaryCalc(), savedEarningType.getIncludeInAverageSalaryCalc());
        assertEquals(earningType.getDescription(), savedEarningType.getDescription());
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> earningTypeService.findById(1L)
        );
        Mockito
                .verify(earningTypeRepository).findById(1L);
    }

    @Test
    void delete_shouldDelete_whenExists() {
        Mockito
                .when(earningTypeRepository.findById(1L))
                .thenReturn(Optional.of(new EarningType()));

        earningTypeService.delete(1L);

        Mockito
                .verify(earningTypeRepository)
                .deleteById(1L);
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

        assertEquals("EarningType not found", ex.getMessage());
        Mockito
                .verify(earningTypeRepository, Mockito.never())
                .deleteById(Mockito.anyLong());
    }

    @Test
    void findHistoryByEarningTypeId() {
        EarningTypeHistory history1 = new EarningTypeHistory();
        history1.setId(1L);

        EarningTypeHistory history2 = new EarningTypeHistory();
        history2.setId(2L);

        Mockito
                .when(earningTypeHistoryRepository
                        .findAllByEarningTypeIdOrderByStartDateDesc(1L))
                .thenReturn(List.of(history1, history2));

        List<EarningTypeHistory> result = earningTypeService.findHistoryByEarningTypeId(1L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        Mockito
                .verify(earningTypeHistoryRepository)
                .findAllByEarningTypeIdOrderByStartDateDesc(1L);
    }
}