package org.glorund.calc.processor.operator;

public class DivineOperator extends BinaryOperator  implements Operator{

    public DivineOperator() {
        super('/',1);
    }

    @Override
    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand/rightOperand;
    }

}
