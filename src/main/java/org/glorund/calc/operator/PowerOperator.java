package org.glorund.calc.operator;

public class PowerOperator extends BinaryOperator {

    public PowerOperator() {
        super('^',2);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return Math.pow(leftOperand,rightOperand);
    }

}
