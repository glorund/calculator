package org.glorund.calc.processor.parser;

import org.glorund.calc.processor.operator.Operator;

public class OperatorToken {
    private int tailIndex = 0;
    private final int index;
    private final String operand;
    private final Operator operator;
    private final String formula;
    private final int operatorIndex;
    public OperatorToken(Operator operator,String operand,String formula, int operatorIndex, int index) {
        super();
        this.formula = formula;
        this.operand = operand;
        this.operator = operator;
        this.operatorIndex = operatorIndex;
        this.index = index;
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
    public int getOperatorIndex() {
        return operatorIndex;
    }
    public void pushTail(int tailIndex) {
        this.tailIndex = tailIndex;
    }
    public int getIndex() {
        return index;
    }
    
    public String getFormula() {
        return formula;
    }

    public boolean complete() {
        return operator == null && operand != null;
    }
}
