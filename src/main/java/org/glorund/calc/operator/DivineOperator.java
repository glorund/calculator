package org.glorund.calc.operator;

public class DivineOperator extends AbstractOperator  implements Operator{

    public DivineOperator() {
        super("/");
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand/rightOperand;
    }

}
