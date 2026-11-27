package com.fintrack.demo.shared.exception;

import java.math.BigDecimal;

/** 422 - a operação ultrapassa o limite do orçamento. */
public class BudgetExceededException extends BusinessRuleException {

    public BudgetExceededException(String message) {
        super("Orçamento excedido", message);
    }

    public BudgetExceededException(BigDecimal limitAmount, BigDecimal totalAmount) {
        this("O total de %s ultrapassa o limite do orçamento de %s"
                .formatted(totalAmount.toPlainString(), limitAmount.toPlainString()));
    }
}
