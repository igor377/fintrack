package com.fintrack.demo.user.exception;

import com.fintrack.demo.shared.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists");
    }
    
}
