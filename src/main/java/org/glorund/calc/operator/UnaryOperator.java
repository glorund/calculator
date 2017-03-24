package org.glorund.calc.operator;

public abstract class UnaryOperator extends AbstractOperator implements Operator {

    public UnaryOperator(char symbol, int priority) {
        super(symbol, priority);
    }

    public abstract double evaluate(double leftOperand);

    @Override
    public final boolean isUnary() {
        return true;
    }

    public abstract char getClosedChar();
}
