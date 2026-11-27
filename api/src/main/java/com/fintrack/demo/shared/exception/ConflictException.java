package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/** 409 - a operação conflita com o estado atual (ex.: e-mail já cadastrado). */
public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, "Conflito", message);
    }
}
