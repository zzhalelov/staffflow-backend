package kz.zzhalelov.staffflow.server.department;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return departmentRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findByIdAndDeletedFalse(departmentId)
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
    @Transactional
    public void delete(long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        department.markAsDeleted(username);
    }

    @Transactional
    public void restore(long id) {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found"));

        if (!department.isDeleted()) {
            throw new ConflictException("Подразделение не удалено");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        department.restore(username);
    }
}