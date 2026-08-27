package com.strayhelp.backend.service;

import com.strayhelp.backend.dto.PetRequest;
import com.strayhelp.backend.dto.PetResponse;
import com.strayhelp.backend.exception.PetNotFoundException;
import com.strayhelp.backend.model.Pet;
import com.strayhelp.backend.model.PetStatus;
import com.strayhelp.backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// @Service marks this as a Spring-managed bean in the service layer
// (business logic sits here, between the controller and the repository).
@Service
// @RequiredArgsConstructor (Lombok) generates a constructor with one
// parameter per "final" field below. Spring sees that single constructor
// and automatically injects the PetRepository bean into it — this is
// "constructor injection", the recommended alternative to @Autowired
// on a field, because it makes dependencies explicit and the class
// easy to unit test (just call `new PetService(mockRepository)`).
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public PetResponse create(PetRequest request) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setGender(request.getGender());
        pet.setSize(request.getSize());
        pet.setDescription(request.getDescription());
        pet.setLocation(request.getLocation());
        pet.setContactPhone(request.getContactPhone());

        // Status is never accepted from the client — it's a server-owned
        // lifecycle field. Every pet starts out as an unverified report;
        // moving it to LISTED/ADOPTED/etc. is a separate, deliberate action.
        pet.setStatus(PetStatus.REPORTED);

        // id, createdAt, updatedAt are left unset here on purpose:
        // @GeneratedValue and Hibernate's @CreationTimestamp/@UpdateTimestamp
        // fill them in during this save() call.
        Pet saved = petRepository.save(pet);

        return toResponse(saved);
    }

    public List<PetResponse> getAll() {
        return petRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PetResponse getById(UUID id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found: " + id));

        return toResponse(pet);
    }

    // Entity -> DTO mapping is pulled into one private helper so all three
    // public methods stay in sync automatically if a field is ever added -
    // without this, each method would need its own copy-pasted mapping,
    // and it would be easy to update one and forget the others.
    private PetResponse toResponse(Pet pet) {
        PetResponse response = new PetResponse();
        response.setId(pet.getId());
        response.setName(pet.getName());
        response.setSpecies(pet.getSpecies());
        response.setBreed(pet.getBreed());
        response.setAge(pet.getAge());
        response.setGender(pet.getGender());
        response.setSize(pet.getSize());
        response.setDescription(pet.getDescription());
        response.setLocation(pet.getLocation());
        response.setContactPhone(pet.getContactPhone());
        response.setStatus(pet.getStatus());
        response.setImageUrl(pet.getImageUrl());
        response.setCreatedAt(pet.getCreatedAt());
        response.setUpdatedAt(pet.getUpdatedAt());
        return response;
    }
}
