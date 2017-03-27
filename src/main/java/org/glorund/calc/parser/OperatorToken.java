package org.glorund.calc.parser;

import org.glorund.calc.operator.Operator;

public class OperatorToken {
    private final String operand;
    private final Operator operator;
    private final int index;
    public OperatorToken(Operator operator,String operand, int index) {
        super();
        this.operand = operand;
        this.operator = operator;
        this.index = index;
    }
    public String getOperand() {
        return operand;
    }
    public Operator getOperator() {
        return operator;
    }
    public int getIndex() {
        return index;
    }
}
