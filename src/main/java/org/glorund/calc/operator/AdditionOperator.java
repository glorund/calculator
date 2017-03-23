package org.glorund.calc.operator;

public class AdditionOperator extends AbstractOperator  implements Operator {

    public AdditionOperator() {
        super("+",0);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand+rightOperand;
    }

}
