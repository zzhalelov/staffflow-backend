package kz.zzhalelov.staffflow.server.absence.vacation;

import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationCreateDto;
import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationMapper;
import kz.zzhalelov.staffflow.server.absence.vacation.dto.VacationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;

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
}