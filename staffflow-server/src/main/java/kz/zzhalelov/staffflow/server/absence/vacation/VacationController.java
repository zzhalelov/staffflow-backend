package kz.zzhalelov.staffflow.server.absence.vacation;

import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationCreateDto;
import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationMapper;
import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacations")
public class VacationController {
    private final VacationService vacationService;
    private final VacationMapper vacationMapper;

    @PostMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public VacationResponseDto create(@RequestBody VacationCreateDto dto,
                                      @PathVariable Long employeeId,
                                      @RequestParam Month month,
                                      @RequestParam LocalDate startDate,
                                      @RequestParam LocalDate endDate) {
        Vacation vacation = vacationMapper.fromCreate(dto);
        return vacationMapper.toResponse(vacationService.create(vacation, employeeId, month, startDate, endDate));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VacationResponseDto> findAll() {
        return vacationService.findAll().stream()
                .map(vacationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{vacationId}")
    @ResponseStatus(HttpStatus.OK)
    public VacationResponseDto findById(@PathVariable Long vacationId) {
        return vacationMapper.toResponse(vacationService.findById(vacationId));
    }

    @DeleteMapping("/{vacationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long vacationId) {
        vacationService.delete(vacationId);
    }
}