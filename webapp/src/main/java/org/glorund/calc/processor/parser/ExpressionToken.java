package org.glorund.calc.processor.parser;

import org.glorund.calc.processor.Expression;

public class ExpressionToken {
    private final Expression expression;
    private final int index;
    public ExpressionToken(Expression expression, int index) {
        super();
        this.expression = expression;
        this.index = index;
    }
    public Expression getExpression() {
        return expression;
    }
    public int getIndex() {
        return index;
    }

}
