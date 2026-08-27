package com.strayhelp.backend.controller;

import com.strayhelp.backend.dto.PetRequest;
import com.strayhelp.backend.dto.PetResponse;
import com.strayhelp.backend.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    // @Valid triggers the validation rules declared on PetRequest's fields;
    // @RequestBody converts the incoming JSON body into a PetRequest object.
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetRequest request) {
        PetResponse created = petService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<PetResponse> getAll() {
        return petService.getAll();
    }

    @GetMapping("/{id}")
    // @PathVariable pulls the {id} segment out of the URL and binds it to this parameter.
    public PetResponse getById(@PathVariable UUID id) {
        return petService.getById(id);
    }
}
