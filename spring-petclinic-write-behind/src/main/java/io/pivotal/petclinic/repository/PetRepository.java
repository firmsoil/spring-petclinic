package io.pivotal.petclinic.repository;

import io.pivotal.petclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
