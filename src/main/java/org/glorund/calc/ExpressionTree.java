package org.glorund.calc;

import org.glorund.calc.operator.Operator;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

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

    @Override
    public String toString() {
        if (operator.isUnary()) {
            return operator.getSymbol()+"("+leftOperand+")";
        } else {
            return "(" + leftOperand + operator.getSymbol() + rightOperand + ")" ;
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ExpressionTree)) {
            return false;
        }
        ExpressionTree castOther = (ExpressionTree) other;
        return new EqualsBuilder().append(leftOperand, castOther.leftOperand)
                .append(rightOperand, castOther.rightOperand).append(operator, castOther.operator).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(leftOperand).append(rightOperand).append(operator).toHashCode();
    }
    
    
}
