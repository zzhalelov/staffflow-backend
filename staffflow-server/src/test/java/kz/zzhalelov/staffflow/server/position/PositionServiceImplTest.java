package kz.zzhalelov.staffflow.server.position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}