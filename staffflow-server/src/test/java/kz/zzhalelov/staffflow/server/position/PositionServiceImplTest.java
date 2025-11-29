package kz.zzhalelov.staffflow.server.position;

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
class PositionServiceImplTest {
    @Mock
    private PositionRepository positionRepository;
    @InjectMocks
    private PositionServiceImpl positionService;

    @Test
    void createPosition_shouldCreate() {
        Position position = new Position();
        position.setId(1L);
        position.setName("Test");

        Mockito
                .when(positionRepository.save(any(Position.class)))
                .thenReturn(position);

        Position savedPosition = positionService.createPosition(position);

        assertEquals(position.getId(), savedPosition.getId());
        assertEquals(position.getName(), savedPosition.getName());
    }

    @Test
    void addPositionIntoStaffSchedule() {
    }

    @Test
    void findAll_shouldReturnList() {
        Position position1 = new Position();
        position1.setId(1L);
        position1.setName("CEO");

        Position position2 = new Position();
        position2.setId(2L);
        position2.setName("CTO");

        List<Position> positions = List.of(position1, position2);

        Mockito
                .when(positionRepository.findAll())
                .thenReturn(positions);
        List<Position> savedPositions = positionService.findAll();
        assertEquals(2, savedPositions.size());
        assertEquals(position1.getId(), savedPositions.get(0).getId());
        assertEquals(position2.getId(), savedPositions.get(1).getId());
    }

    @Test
    void findById() {
        Position position = new Position();
        position.setId(1L);
        position.setName("Test");

        Mockito
                .when(positionRepository.findById(1L))
                .thenReturn(Optional.of(position));

        Position savedPosition = positionService.findById(1L);

        assertEquals(position.getId(), savedPosition.getId());
        assertEquals(position.getName(), savedPosition.getName());
    }

    @Test
    void update() {
        Position existingPosition = new Position();
        existingPosition.setId(1L);

        Position updatedPosition = new Position();
        updatedPosition.setName("new name");

        Mockito
                .when(positionRepository.findById(1L))
                .thenReturn(Optional.of(existingPosition));

        positionService.update(1L, updatedPosition);

        assertEquals("new name", existingPosition.getName());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        Mockito
                .when(positionRepository.findById(1L))
                .thenReturn(Optional.of(new Position()));

        positionService.delete(1L);

        Mockito
                .verify(positionRepository)
                .deleteById(1L);
    }

    @Test
    void delete_shouldThrow_whenNotExists() {
        Mockito
                .when(positionRepository.findById(1L))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> positionService.delete(1L)
        );

        assertEquals("Position doesn't exist", ex.getMessage());
        Mockito
                .verify(positionRepository, Mockito.never())
                .deleteById(Mockito.anyLong());
    }
}