package org.glorund.calc.operator;

public abstract class BinaryOperator extends AbstractOperator implements Operator {

    public BinaryOperator(char symbol, int priority) {
        super(symbol, priority);
    }

    public abstract double evaluate(double leftOperand, double rightOperand);

    @Override
    public final boolean isUnary() {
        return false;
    }
}
