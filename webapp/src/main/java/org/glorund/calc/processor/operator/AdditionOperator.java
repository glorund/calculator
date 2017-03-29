package org.glorund.calc.processor.operator;

public class AdditionOperator extends BinaryOperator {

    public AdditionOperator() {
        super('+',0);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand+rightOperand;
    }

}
