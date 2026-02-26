package kz.zzhalelov.staffflow.server.department;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department create(Department department) {
        log.info("Creating department: {}", department.getName());
        departmentRepository.findByNameIgnoreCase(department.getName().trim())
                .ifPresent(d -> {
                    log.warn("Department with name {} already exists", department.getName());
                    throw new ConflictException("Подразделение с таким названием уже существует: " + department.getName());
                });
        Department saved = departmentRepository.save(department);
        log.info("Department created id={} name='{}'", saved.getId(), saved.getName());
        return saved;
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        log.debug("Fetching departments page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return departmentRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Department findById(long departmentId) {
        log.debug("Fetching department id={}", departmentId);
        return departmentRepository.findByIdAndDeletedFalse(departmentId)
                .orElseThrow(() -> {
                    log.warn("Department not found id={}", departmentId);
                    return new NotFoundException("Отдел не найден");
                });
    }

    @Override
    public Department update(long id, Department updated) {
        log.info("Attempt to update department's name={}", updated.getName());
        departmentRepository.findByNameIgnoreCase(updated.getName().trim())
                .ifPresent(d -> {
                    log.warn("Department with name {} already exists", updated.getName());
                    throw new ConflictException("Подразделение с таким названием уже существует: " + updated.getName());
                });

        log.info("Attempt to update department id={}", id);
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Updating failed: department not found id={}", id);
                    return new NotFoundException("Отдел не найден");
                });
        existing.setName(updated.getName());
        Department saved = departmentRepository.save(existing);
        log.info("Department updated id={} newName='{}'", saved.getId(), saved.getName());
        return saved;
    }

    @Override
    @Transactional
    public void delete(long departmentId) {
        log.info("Attempt to soft delete department id={}", departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    log.warn("Deleting failed: department not found id={}", departmentId);
                    return new NotFoundException("Department not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        department.markAsDeleted(username);
        log.info("Department id={} soft deleted by {}", departmentId, username);
    }

    @Transactional
    public void restore(long id) {
        log.info("Attempt to restore department id={}", id);
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Restore failed: department not found id={}", id);
                    return new NotFoundException("Department not found");
                });

        if (!department.isDeleted()) {
            log.warn("Restore failed: department id={} is not deleted", id);
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
        log.info("Department id={} restored by {}", id, username);
    }
}