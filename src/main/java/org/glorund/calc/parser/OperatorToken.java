package org.glorund.calc.parser;

import org.glorund.calc.operator.Operator;

public class OperatorToken {
    private final String operand;
    private final Operator operator;
    private int tailIndex = 0;
    private final String formula;
    private final int index;
    public OperatorToken(Operator operator,String operand,String formula, int index) {
        super();
        this.formula = formula;
        this.operand = operand;
        this.operator = operator;
        this.index = index;
    }
    public String getTail() {
        return formula.substring(tailIndex);
    }
    public String getOperand() {
        return operand;
    }
    public Operator getOperator() {
        return operator;
    }
    public int getTailIndex() {
        return tailIndex;
    }
    public int getIndex() {
        return index;
    }
    public void pushTail(int tailIndex) {
        this.tailIndex = tailIndex;
    }
}
