package kz.zzhalelov.staffflow.server.vacation;

import io.swagger.v3.oas.annotations.Operation;
import kz.zzhalelov.staffflow.server.vacation.dto.VacationCreateDto;
import kz.zzhalelov.staffflow.server.vacation.dto.VacationMapper;
import kz.zzhalelov.staffflow.server.vacation.dto.VacationResponseDto;
import kz.zzhalelov.staffflow.server.vacation.dto.VacationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacations")
public class VacationController {
    private final VacationService vacationService;
    private final VacationMapper vacationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public VacationResponseDto create(@RequestParam Long employeeId,
                                      @RequestParam Long organizationId,
                                      @RequestBody VacationCreateDto dto) {
        Vacation vacation = vacationMapper.fromCreate(dto);
        return vacationMapper
                .toResponse(vacationService.create(employeeId, organizationId, vacation));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VacationResponseDto> findAll() {
        return vacationService.findAll()
                .stream()
                .map(vacationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{vacationId}")
    @ResponseStatus(HttpStatus.OK)
    public VacationResponseDto findById(@PathVariable Long vacationId) {
        return vacationMapper.toResponse(vacationService.findById(vacationId));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения об отпуске")
    public VacationResponseDto update(@PathVariable long id,
                                      @RequestBody VacationUpdateDto dto) {
        Vacation vacation = vacationMapper.fromUpdate(dto);
        return vacationMapper.toResponse(vacationService.update(id, vacation));
    }

    @DeleteMapping("/{vacationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long vacationId) {
        vacationService.delete(vacationId);
    }
}