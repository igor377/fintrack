package com.fintrack.demo.shared.exception;

import org.springframework.http.HttpStatus;

/** 422 - a requisição é válida, mas viola uma regra de negócio. */
public class BusinessRuleException extends ApiException {

    public BusinessRuleException(String message) {
        this("Regra de negócio violada", message);
    }

    protected BusinessRuleException(String title, String message) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, title, message);
    }
}
