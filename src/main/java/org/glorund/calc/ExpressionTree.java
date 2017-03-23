package org.glorund.calc;

import org.glorund.calc.operator.Operator;

public class ExpressionTree implements ExpressionNode {
    // The left node in the tree.
    private ExpressionNode leftOperand = null;

    // The right node in the tree.
    private ExpressionNode rightOperand = null;

    // The operator for the two operands.
    private final Operator operator;

    public ExpressionTree(Operator operator) {
        super();
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setLeftOperand(ExpressionNode leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setRightOperand(ExpressionNode rightOperand) {
        this.rightOperand = rightOperand;
    }

    public double evaluate() {
        Double value = null;
        if (operator.isUnary()) {
            value =  operator.evaluate(leftOperand.evaluate());
        } else {
            value = operator.evaluate(leftOperand.evaluate(), rightOperand.evaluate());
        }
        return value;
    }
}
