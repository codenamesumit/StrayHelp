package com.strayhelp.backend.repository;

import com.strayhelp.backend.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Spring Data generates the implementation of this interface at runtime — no code needed here
public interface PetRepository extends JpaRepository<Pet, UUID> {
}
