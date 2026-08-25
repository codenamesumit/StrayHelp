package com.strayhelp.backend.dto;

import com.strayhelp.backend.model.Gender;
import com.strayhelp.backend.model.PetSize;
import com.strayhelp.backend.model.PetStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

// PetResponse = the shape the server sends BACK to the client.
// No validation annotations — this is outbound data, nothing to validate.
@Getter
@Setter
public class PetResponse {

    // Phase 1 exposes every field (no auth yet). Later, to hide sensitive
    // fields on public listings, delete contactPhone/location FROM THIS
    // CLASS ONLY — the entity and DB table stay untouched. That decoupling
    // is the whole reason DTOs exist.

    private UUID id;              // server-generated, safe to expose now
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private Gender gender;
    private PetSize size;
    private String description;
    private String location;
    private String contactPhone;
    private PetStatus status;     // server-assigned lifecycle state
    private String imageUrl;
    private Instant createdAt;    // server-set timestamp
    private Instant updatedAt;    // server-set timestamp
}
