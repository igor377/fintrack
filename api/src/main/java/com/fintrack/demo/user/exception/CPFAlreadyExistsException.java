package com.fintrack.demo.user.exception;

import com.fintrack.demo.shared.exception.ConflictException;

public class CPFAlreadyExistsException extends ConflictException {
    public CPFAlreadyExistsException(String cpf) {
        super("CPF already exists");
    }
    
}
