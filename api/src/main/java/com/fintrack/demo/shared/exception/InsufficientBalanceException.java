package com.fintrack.demo.shared.exception;

/** 422 - a conta não tem saldo suficiente para a operação. */
public class InsufficientBalanceException extends BusinessRuleException {

    public InsufficientBalanceException(String message) {
        super("Saldo insuficiente", message);
    }
}
