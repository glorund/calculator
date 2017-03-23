package org.glorund.calc.operator;

public class SubtractionOperator extends AbstractOperator  implements Operator{

    public SubtractionOperator() {
        super("-",0);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand-rightOperand;
    }

}
