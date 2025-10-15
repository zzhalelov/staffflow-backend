package kz.zzhalelov.staffflow.server.payroll;

import kz.zzhalelov.staffflow.server.payroll.dto.PayrollMapper;
import kz.zzhalelov.staffflow.server.payroll.dto.PayrollResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payrolls")
public class PayrollController {
    private final PayrollService payrollService;
    private final PayrollMapper payrollMapper;

    @GetMapping
    public List<PayrollResponseDto> findAll() {
        return payrollService.findAll()
                .stream()
                .map(payrollMapper::toResponse)
                .collect(Collectors.toList());
    }
}