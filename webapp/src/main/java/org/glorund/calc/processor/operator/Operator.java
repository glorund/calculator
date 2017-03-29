package org.glorund.calc.processor.operator;

public interface Operator {
    public boolean isUnary();
    public char getSymbol();
    public int getPriority();
}
