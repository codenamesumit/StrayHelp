package com.strayhelp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

// @RestControllerAdvice is a global catch-all across all controllers; this
// maps PetNotFoundException to HTTP 404 instead of the default 500, keeping
// HTTP concerns out of the service layer.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> handleNotFound(PetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Spring throws MethodArgumentNotValidException when an @Valid
    // @RequestBody argument (our PetRequest) fails its Bean Validation
    // constraints (@NotBlank, @NotNull, etc.) before the controller method
    // ever runs. It carries a BindingResult with one FieldError per failed
    // constraint; we flatten that into a simple field -> message map (first
    // message wins if a field has more than one violation) so callers get
    // a response like {"species": "must not be blank"} instead of having to
    // parse Spring's default, more verbose error structure.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
