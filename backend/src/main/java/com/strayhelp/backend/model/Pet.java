package com.strayhelp.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id
    // generates the primary key value (a UUID) automatically before insert
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @NotBlank
    private String species;

    private String breed;

    private Integer age;

    @NotNull
    // stores the enum name (e.g. "MALE") as a string column instead of its ordinal
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetSize size;

    @NotBlank
    @Column(length = 2000)
    private String description;

    @NotBlank
    private String location;

    @NotBlank
    private String contactPhone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    private String imageUrl;

    @CreationTimestamp
    // Hibernate sets this to the current time on insert; never changes after
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    // Hibernate refreshes this to the current time on every update
    private Instant updatedAt;
}
