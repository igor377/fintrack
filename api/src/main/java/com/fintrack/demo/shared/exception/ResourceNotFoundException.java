package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/** 404 - o recurso solicitado não existe. */
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "Recurso não encontrado", message);
    }

    public ResourceNotFoundException(String resource, Object id) {
        this("%s não encontrado(a) com id: %s".formatted(resource, id));
    }
}
