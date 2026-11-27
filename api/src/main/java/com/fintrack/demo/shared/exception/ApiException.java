package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Base de todas as exceções da aplicação. Cada subclasse define o status HTTP
 * e o título que o {@link GlobalExceptionHandler} devolve na resposta.
 */
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String title;

    protected ApiException(HttpStatus status, String title, String message) {
        super(message);
        this.status = status;
        this.title = title;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }
}
