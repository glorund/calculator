package org.glorund.calc;

public class ExpressionValue implements ExpressionNode{
    private double value;
    private final String name;

    public ExpressionValue(final String name) {
        super();
        this.name = name;
    }

    @Override
    public double evaluate() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{"+name +"=" + value + "}";
    }
    
}
