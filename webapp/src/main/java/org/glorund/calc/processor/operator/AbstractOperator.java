package org.glorund.calc.processor.operator;

public abstract class AbstractOperator implements Operator {
    private final char symbol;
    private final int priority;

    public AbstractOperator(final char symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    @Override
    public final char getSymbol() {
        return symbol;
    }

    @Override
    public final int getPriority() {
        return priority;
    }

}
