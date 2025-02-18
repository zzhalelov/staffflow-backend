package kz.zzhalelov.hrmsspringjpapractice.repository;

import kz.zzhalelov.hrmsspringjpapractice.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
}