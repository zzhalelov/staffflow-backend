package kz.zzhalelov.staffflow.server.payroll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;

    @Override
    public Payroll createPayroll(Long organizationId, Month month, int year) {
        return null;
    }

    @Override
    public Payroll addEmployeeToPayroll(Long employeeId, Long payrollId) {
        return null;
    }

    @Override
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }
}