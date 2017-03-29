package org.glorund.calc.processor.operator;

public class StatementOperator extends UnaryOperator implements Operator {
    private static final  char CLOSED_CHAR=')';
    
    public StatementOperator() {
        super('(',4);
    }

    
    @Override
    public char getClosedChar() {
        return CLOSED_CHAR;
    }

    @Override
    public double evaluate(double leftOperand) {
        return leftOperand;
    }
}
