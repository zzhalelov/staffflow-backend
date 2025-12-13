package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegulatoryIndicatorServiceImplTest {
    @Mock
    private RegulatoryIndicatorRepository indicatorRepository;
    @InjectMocks
    private RegulatoryIndicatorServiceImpl indicatorService;

    @Test
    void create_shouldCreate() {
        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setId(1L);
        indicator.setDate(LocalDate.of(2025, 1, 1));
        indicator.setMrpValue(BigDecimal.valueOf(3932));
        indicator.setMzpValue(BigDecimal.valueOf(85000));
        indicator.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorRepository.save(any(RegulatoryIndicator.class)))
                .thenReturn(indicator);

        RegulatoryIndicator savedIndicator = indicatorService.create(indicator);

        assertEquals(indicator.getId(), savedIndicator.getId());
        assertEquals(indicator.getDate(), savedIndicator.getDate());
        assertEquals(indicator.getMrpValue(), savedIndicator.getMrpValue());
        assertEquals(indicator.getMzpValue(), savedIndicator.getMzpValue());
        assertEquals(indicator.getDescription(), savedIndicator.getDescription());
    }

    @Test
    void findAll_shouldReturnList() {
        RegulatoryIndicator indicator1 = new RegulatoryIndicator();
        indicator1.setId(1L);
        indicator1.setDate(LocalDate.of(2025, 1, 1));
        indicator1.setMrpValue(BigDecimal.valueOf(3932));
        indicator1.setMzpValue(BigDecimal.valueOf(85000));
        indicator1.setDescription("МРП и МЗП на 2025 год");

        RegulatoryIndicator indicator2 = new RegulatoryIndicator();
        indicator2.setId(2L);
        indicator2.setDate(LocalDate.of(2024, 1, 1));
        indicator2.setMrpValue(BigDecimal.valueOf(3692));
        indicator2.setMzpValue(BigDecimal.valueOf(85000));
        indicator2.setDescription("МРП и МЗП на 2024 год");

        List<RegulatoryIndicator> indicators = List.of(indicator1, indicator2);

        Mockito
                .when(indicatorRepository.findAll())
                .thenReturn(indicators);

        List<RegulatoryIndicator> savedIndicators = indicatorService.findAll();
        assertEquals(2, savedIndicators.size());
        assertEquals(indicator1.getId(), savedIndicators.get(0).getId());
        assertEquals(indicator2.getId(), savedIndicators.get(1).getId());
    }

    @Test
    void findById() {
        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setId(1L);
        indicator.setDate(LocalDate.of(2025, 1, 1));
        indicator.setMrpValue(BigDecimal.valueOf(3932));
        indicator.setMzpValue(BigDecimal.valueOf(85000));
        indicator.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorRepository.findById(1L))
                .thenReturn(Optional.of(indicator));

        RegulatoryIndicator savedIndicator = indicatorService.findById(1L);
        assertEquals(indicator.getId(), savedIndicator.getId());
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        long indicatorId = 1;

        Mockito
                .when(indicatorRepository.findById(indicatorId))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> indicatorService.findById(indicatorId)
        );

        assertEquals("Регламентированный показатель с данным id не найден", ex.getMessage());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        long indicatorId = 1L;

        Mockito
                .when(indicatorRepository.existsById(1L))
                .thenReturn(true);

        assertDoesNotThrow(() -> indicatorService.deleteById(indicatorId));
        Mockito.verify(indicatorRepository).deleteById(indicatorId);
    }

    @Test
    void delete_shouldThrow_WhenNotFound() {
        Mockito
                .when(indicatorRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(NotFoundException.class, () -> indicatorService.deleteById(1L));
        Mockito.verify(indicatorRepository, Mockito.never()).deleteById(1L);
    }

    @Test
    void update_shouldUpdateIndicator() {
        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setId(1L);
        indicator.setDate(LocalDate.of(2025, 1, 1));
        indicator.setMrpValue(BigDecimal.valueOf(3932));
        indicator.setMzpValue(BigDecimal.valueOf(85000));
        indicator.setDescription("МРП и МЗП на 2025 год");

        Mockito
                .when(indicatorRepository.findById(1L))
                .thenReturn(Optional.of(indicator));

        Mockito
                .when(indicatorRepository.save(Mockito.any(RegulatoryIndicator.class)))
                .thenReturn(indicator);

        RegulatoryIndicator savedIndicator = indicatorService.update(1L, indicator);
        assertEquals(indicator.getId(), savedIndicator.getId());
        assertEquals(indicator.getMzpValue(), savedIndicator.getMzpValue());
        assertEquals(indicator.getMrpValue(), savedIndicator.getMrpValue());
        assertEquals(indicator.getDescription(), savedIndicator.getDescription());
        assertEquals(indicator.getDate(), savedIndicator.getDate());
    }

    @Test
    void update_shouldThrow_whenNotExists() {
        long indicatorId = 1L;

        Mockito
                .when(indicatorRepository.findById(indicatorId))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> indicatorService.update(indicatorId, new RegulatoryIndicator())
        );

        assertEquals("Regulatory Indicator Not Found", ex.getMessage());
        Mockito
                .verify(indicatorRepository)
                .findById(indicatorId);
    }
}