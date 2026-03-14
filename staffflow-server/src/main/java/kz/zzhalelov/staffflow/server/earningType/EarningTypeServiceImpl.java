package kz.zzhalelov.staffflow.server.earningType;

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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarningTypeServiceImpl implements EarningTypeService {
    private final EarningTypeRepository earningTypeRepository;
    private final EarningTypeHistoryRepository earningTypeHistoryRepository;

    //create
    @Override
    public EarningType create(EarningType earningType) {
        log.info("Creating earning type with name: {}", earningType.getName());
        if (earningTypeRepository.existsByNameIgnoreCase(earningType.getName().trim())) {
            log.warn("Creating: Earning type with name {} already exists", earningType.getName());
            throw new ConflictException("Earning type with this name already exists: " + earningType.getName());
        }

        log.info("Creating earning type with code: {}", earningType.getCode());
        if (earningTypeRepository.existsByCodeIgnoreCase(earningType.getCode().trim())) {
            log.warn("Creating: Earning type with code {} already exists", earningType.getCode());
            throw new ConflictException("Earning type with this code already exists: " + earningType.getCode());
        }

        EarningType saved = earningTypeRepository.save(earningType);
        log.info("Earning type created id={} name='{}'", saved.getId(), saved.getName());
        return saved;
    }

    //update department
    @Override
    public EarningType update(long id, EarningType updated) {
        log.info("Attempt to update earning type id={}", id);

        if (earningTypeRepository.existsByNameIgnoreCase(updated.getName().trim())) {
            log.warn("Updating: Earning type with name {} already exists", updated.getName());
            throw new ConflictException("Earning type with this name already exists: " + updated.getName());
        }

        if (earningTypeRepository.existsByCodeIgnoreCase(updated.getCode().trim())) {
            log.warn("Updating: Earning type with code {} already exists", updated.getCode());
            throw new ConflictException("Earning type with this code already exists: " + updated.getCode());
        }

        EarningType existing = earningTypeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Updating failed: earning type not found id={}", id);
                    return new NotFoundException("Earning Type not found");
                });
        merge(existing, updated);
        EarningType saved = earningTypeRepository.save(existing);
        log.info("Earning type updated id={}", saved.getId());
        return saved;
    }

    //find all with pagination
    @Override
    public Page<EarningType> findAll(Pageable pageable) {
        log.debug("Fetching earning types page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return earningTypeRepository.findAllByDeletedFalse(pageable);
    }

    //find department by id
    @Override
    public EarningType findById(long id) {
        log.debug("Fetching earning type id={}", id);
        return earningTypeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Earning type not found id={}", id);
                    return new NotFoundException("EarningType not found");
                });
    }

    //soft delete
    @Override
    @Transactional
    public void delete(long id) {
        log.info("Attempt to soft delete earning type id={}", id);
        EarningType earningType = earningTypeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Deleting failed: earning type not found id={}", id);
                    return new NotFoundException("Earning type not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        earningType.markAsDeleted(username);
        log.info("Earning type id={} soft deleted by {}", id, username);
    }

    //restore department
    @Override
    @Transactional
    public void restore(long id) {
        log.info("Attempt to restore earning type id={}", id);
        EarningType earningType = earningTypeRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Restore failed: earning type not found id={}", id);
                    return new NotFoundException("Earning type not found");
                });

        if (!earningType.isDeleted()) {
            log.warn("Restore failed: earning type id={} is not deleted", id);
            throw new ConflictException("Earning type not deleted");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        earningType.restore(username);
        log.info("Earning type id={} restored by {}", id, username);
    }

    @Override
    public List<EarningTypeHistory> findHistoryByEarningTypeId(Long earningTypeId) {
        return earningTypeHistoryRepository.findAllByEarningTypeIdOrderByStartDateDesc(earningTypeId);
    }

    private void merge(EarningType existing, EarningType updated) {
        if (updated.getName() != null && !updated.getName().isBlank()) {
            existing.setName(updated.getName());
        }
        if (updated.getCode() != null && !updated.getCode().isBlank()) {
            existing.setCode(updated.getCode());
        }
        if (updated.getIncludeInFot() != null) {
            existing.setIncludeInFot(updated.getIncludeInFot());
        }
        if (updated.getIncludeInAverageSalaryCalc() != null) {
            existing.setIncludeInAverageSalaryCalc(updated.getIncludeInAverageSalaryCalc());
        }
        if (updated.getIndexable() != null) {
            existing.setIndexable(updated.getIndexable());
        }
        if (updated.getDescription() != null && !updated.getDescription().isBlank()) {
            existing.setDescription(updated.getDescription());
        }
    }
}