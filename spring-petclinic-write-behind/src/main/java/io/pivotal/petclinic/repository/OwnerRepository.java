package io.pivotal.petclinic.repository;

import io.pivotal.petclinic.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
