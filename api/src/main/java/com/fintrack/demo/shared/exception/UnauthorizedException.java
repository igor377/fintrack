package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/** 401 - falha de autenticação (ex.: credenciais inválidas no login). */
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, "Não autorizado", message);
    }
}
