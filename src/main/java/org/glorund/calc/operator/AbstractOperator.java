package org.glorund.calc.operator;

public class AbstractOperator {
    private final String symbol;
    private final boolean unary;

    public AbstractOperator(final String symbol, boolean unary) {
        this.symbol = symbol;
        this.unary = unary;
    }

    public AbstractOperator(String symbol) {
        this(symbol,false);
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

}
