package org.glorund.calc.operator;

public interface Operator {
    public boolean isUnary();
    public char getSymbol();
    public int getPriority();
}
