package org.glorund.calc.operator;

public class MultiplyOperator extends AbstractOperator  implements Operator{

    public MultiplyOperator() {
        super("*");
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand*rightOperand;
    }

}
