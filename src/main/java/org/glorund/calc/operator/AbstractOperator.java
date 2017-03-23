package org.glorund.calc.operator;

public class AbstractOperator {
    private final String symbol;
    private final boolean unary;
    private final int priority;

    public AbstractOperator(final String symbol, int priority, boolean unary) {
        this.symbol = symbol;
        this.priority = priority;
        this.unary = unary;
    }

    public AbstractOperator(String symbol,int priority) {
        this(symbol,priority,false);
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isUnary() {
        return unary;
    }
    
    public double evaluate(double leftOperand, double rightOperand) {
        return 0;
    }

    public double evaluate(double leftOperand) {
        return 0;
    }
    
    public int getPriority() {
        return priority;
    }

}
