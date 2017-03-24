package org.glorund.calc;

import org.glorund.calc.operator.BinaryOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.UnaryOperator;

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

    public ExpressionTree(Operator operator, ExpressionNode leftOperand) {
        super();
        this.operator = operator;
        this.leftOperand = leftOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public ExpressionNode getLeftOperand() {
        return leftOperand;
    }

    public ExpressionNode getRightOperand() {
        return rightOperand;
    }

    public void setLeftOperand(ExpressionNode leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setRightOperand(ExpressionNode rightOperand) {
        this.rightOperand = rightOperand;
    }

    public double evaluate() {
        Double value;
        if (operator.isUnary()) {
            value =  ((UnaryOperator) operator).evaluate(leftOperand.evaluate());
        } else {
            value = ((BinaryOperator) operator).evaluate(leftOperand.evaluate(), rightOperand.evaluate());
        }
        return value;
    }

    @Override
    public boolean isValid() {
        if (operator.isUnary()) {
            return leftOperand.isValid();
        }
        return leftOperand != null && leftOperand.isValid() && rightOperand != null && rightOperand.isValid();
    }

    @Override
    public String toString() {
        if (operator.isUnary()) {
            return String.valueOf(operator.getSymbol())+leftOperand+((UnaryOperator)operator).getClosedChar();
        } else {
            return "(" + leftOperand + operator.getSymbol() + rightOperand + ")" ;
        }
    }

}
