package org.glorund.calc.parser;

import org.glorund.calc.operator.Operator;

public class OperatorToken {
    private final Operator operator;
    private final int index;
    public OperatorToken(Operator operator, int index) {
        super();
        this.operator = operator;
        this.index = index;
    }
    public Operator getOperator() {
        return operator;
    }
    public int getIndex() {
        return index;
    }
    
}
