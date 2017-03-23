package org.glorund.calc;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sourceforge.jeval.NextOperator;

import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.SubtractionOperator;

@Component
public class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    
    private final List<Operator> operators = new ArrayList();
    
    public Parser() {
        super();
        operators.add(new AdditionOperator());
        operators.add(new SubtractionOperator());
    }

    public Expression parse(String formula) {
        List<ExpressionValue> values = new ArrayList<>();
        int formulaLength = formula.length();
        int pos = 0;
        while (pos < formulaLength) {
            
        }
        return new Expression(null, values);
    }

    private Operator getNextOperator(final String formula, final int start) {
        
        return null;
    }
    
    @Deprecated
    public Expression oldParse(String formula) {
        List<ExpressionValue> values = new ArrayList<>();
        ExpressionValue value;
        ExpressionTree leaf = new ExpressionTree(new AdditionOperator());
        value = new ExpressionValue();
        leaf.setLeftOperand(value);
        values.add(value);
        value = new ExpressionValue();
        leaf.setRightOperand(value);
        values.add(value);
        ExpressionTree tree = new ExpressionTree(new SubtractionOperator());
        tree.setLeftOperand(leaf);
        value = new ExpressionValue();
        tree.setRightOperand(value);
        values.add(value);
        
        return new Expression(tree,values);
    }

}
