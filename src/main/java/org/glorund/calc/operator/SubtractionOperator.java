package org.glorund.calc.operator;

public class SubtractionOperator extends BinaryOperator  implements Operator{

    public SubtractionOperator() {
        super('-',0);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand-rightOperand;
    }

}
