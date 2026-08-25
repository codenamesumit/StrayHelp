package com.strayhelp.backend.dto;

import com.strayhelp.backend.model.Gender;
import com.strayhelp.backend.model.PetSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// PetRequest = the shape a client is ALLOWED to send when creating a pet.
// This is a plain POJO, NOT a JPA entity — no @Entity, no @Id, no table.
@Getter
@Setter
public class PetRequest {

    // NOTE: no id, status, createdAt, updatedAt here on purpose.
    // Those are server-controlled — letting clients set them would allow
    // forging IDs or backdating records.

    private String name;          // nullable — a stray often has no name yet

    @NotBlank                     // rejects null, empty, and blank strings
    private String species;

    private String breed;         // nullable

    private Integer age;          // nullable — age of a stray is often unknown

    @NotNull                      // enum: value must be present, but @NotBlank
    private Gender gender;        // doesn't apply to non-String types

    @NotNull
    private PetSize size;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotBlank
    private String contactPhone;  // String, never numeric (leading zeros, +91)
}
