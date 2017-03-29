package org.glorund.calc.processor.operator;

public class MultiplyOperator extends BinaryOperator implements Operator{

    public MultiplyOperator() {
        super('*',1);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand*rightOperand;
    }

}
