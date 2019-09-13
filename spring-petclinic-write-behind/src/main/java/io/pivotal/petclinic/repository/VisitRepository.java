package io.pivotal.petclinic.repository;

import io.pivotal.petclinic.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
