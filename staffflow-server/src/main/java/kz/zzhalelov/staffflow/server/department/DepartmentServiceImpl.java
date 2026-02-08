package kz.zzhalelov.staffflow.server.department;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department create(Department department) {
        departmentRepository.findByNameIgnoreCase(department.getName())
                .ifPresent(d -> {
                    throw new ConflictException("Подразделение с таким названием уже существует: " + department.getName());
                });
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Отдел не найден"));
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(long departmentId) {
        if (departmentRepository.findById(departmentId).isPresent()) {
            departmentRepository.deleteById(departmentId);
        } else {
            throw new NotFoundException("Отдел не найден");
        }
    }
}