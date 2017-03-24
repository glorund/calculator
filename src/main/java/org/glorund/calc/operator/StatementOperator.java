package org.glorund.calc.operator;

public class StatementOperator extends UnaryOperator implements Operator {
    private final char closedChar=')';
    
    public StatementOperator() {
        super('(',4);
    }

    
    @Override
    public char getClosedChar() {
        return closedChar;
    }

    @Override
    public double evaluate(double leftOperand) {
        return leftOperand;
    }
}
