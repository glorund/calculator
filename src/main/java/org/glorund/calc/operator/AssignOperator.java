package org.glorund.calc.operator;

public class AssignOperator extends AbstractOperator implements Operator {
    public AssignOperator() {
        super("=",0,true);
    }

    @Override
    public double evaluate(double leftOperand) {
        return leftOperand;
    }
}
