package org.glorund.calc;

public class ExpressionValue implements ExpressionNode{
    private double value;

    public ExpressionValue() {
        super();
    }

    @Override
    public double evaluate() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
