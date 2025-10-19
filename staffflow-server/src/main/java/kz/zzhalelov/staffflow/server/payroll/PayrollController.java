package kz.zzhalelov.staffflow.server.payroll;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.payroll.dto.PayrollMapper;
import kz.zzhalelov.staffflow.server.payroll.dto.PayrollResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payrolls")
@Tag(name = "Payrolls", description = "Начисление заработной платы")
public class PayrollController {
    private final PayrollService payrollService;
    private final PayrollMapper payrollMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать документ начисление")
    public PayrollResponseDto createPayroll(@RequestParam Long organizationId,
                                            @RequestParam Month month,
                                            @RequestParam int year) {
        return payrollMapper.toResponseDto(payrollService.createPayroll(organizationId, month, year));
    }

    //add employee into payroll
    @PostMapping("/{payrollId}/employee")
    @Operation(summary = "Добавить сотрудника в начисление")
    public PayrollResponseDto addEmployee(@PathVariable Long payrollId,
                                          @RequestParam Long employeeId) {
        Payroll payroll = payrollService.addEmployeeToPayroll(payrollId, employeeId);
        return payrollMapper.toResponseDto(payroll);
    }

    //add details into payroll
    @PostMapping("/{payrollId}/employee/{employeeId}/detail")
    @Operation(summary = "Добавить детали в начисление")
    public PayrollResponseDto addDetail(@PathVariable Long payrollId,
                                        @PathVariable Long employeeId,
                                        @RequestParam Long workedDays,
                                        @RequestParam Long plannedDays) {
        Payroll payroll = payrollService.addDetail(payrollId, employeeId, workedDays, plannedDays);
        return payrollMapper.toResponseDto(payroll);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PayrollResponseDto> findAll() {
        return payrollService.findAll()
                .stream()
                .map(payrollMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление начисления заработной платы")
    public void deletePayroll(@PathVariable Long id) {
        payrollService.delete(id);
    }
}