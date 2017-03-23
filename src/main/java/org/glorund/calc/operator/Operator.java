package org.glorund.calc.operator;

public interface Operator {
    public abstract double evaluate(double leftOperand, double rightOperand);
    public abstract double evaluate(double leftOperand);
    public boolean isUnary();
    public abstract String getSymbol();
    public abstract int getPriority();
}
