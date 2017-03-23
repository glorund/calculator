package org.glorund.calc.operator;

public class DivineOperator extends AbstractOperator  implements Operator{

    public DivineOperator() {
        super("/",1);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand/rightOperand;
    }

}
