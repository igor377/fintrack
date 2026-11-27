package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/** 403 - usuário autenticado, mas sem permissão sobre o recurso. */
public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, "Acesso negado", message);
    }
}
