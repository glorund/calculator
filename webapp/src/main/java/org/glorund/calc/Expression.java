package org.glorund.calc;

import java.util.List;

public class Expression {
    private final List<ExpressionValue> values;
    private final ExpressionTree tree;
    
    
    public Expression(ExpressionTree tree,List<ExpressionValue> values) {
        super();
        this.tree = tree;
        this.values = values;
    }
    public ExpressionTree getTree() {
        return tree;
    }
    public List<ExpressionValue> getValues() {
        return values;
    }
    
    
}
