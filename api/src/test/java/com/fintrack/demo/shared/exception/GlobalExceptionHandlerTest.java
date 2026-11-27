package com.fintrack.demo.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void emailConstraintHasSpecificMessage() {
        assertConflict("users_email_key", "E-mail já cadastrado");
    }

    @Test
    void cpfConstraintHasSpecificMessage() {
        assertConflict("unique_cpf", "CPF já cadastrado");
    }

    @Test
    void unknownConstraintHasGenericMessage() {
        assertConflict("outra_constraint", "Registro em conflito com dados existentes");
    }

    private void assertConflict(String constraint, String expectedDetail) {
        var cause = new ConstraintViolationException("violação", new SQLException(), constraint);

        ProblemDetail problem = handler.handleDataIntegrity(new DataIntegrityViolationException("violação", cause));

        assertEquals(409, problem.getStatus());
        assertEquals(expectedDetail, problem.getDetail());
    }
}
