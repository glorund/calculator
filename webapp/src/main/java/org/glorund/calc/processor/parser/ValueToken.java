package org.glorund.calc.processor.parser;

import org.glorund.calc.processor.ExpressionValue;

public class ValueToken {
    private final boolean constant;
    private final ExpressionValue value;
    public ValueToken(boolean constant, ExpressionValue value) {
        super();
        this.constant = constant;
        this.value = value;
    }
    public boolean isConstant() {
        return constant;
    }
    public ExpressionValue getValue() {
        return value;
    }
    
}
