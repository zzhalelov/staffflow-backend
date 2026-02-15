package kz.zzhalelov.staffflow.server.department;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department create(Department department) {
        departmentRepository.findByNameIgnoreCase(department.getName().trim())
                .ifPresent(d -> {
                    throw new ConflictException("Подразделение с таким названием уже существует: " + department.getName());
                });
        return departmentRepository.save(department);
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Отдел не найден"));
    }

    @Override
    public Department update(long id, Department updated) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Отдел не найден"));
        existing.setName(updated.getName());
        return departmentRepository.save(existing);
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