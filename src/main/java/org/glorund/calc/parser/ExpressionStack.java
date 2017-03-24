package org.glorund.calc.parser;

import java.util.ArrayList;
import java.util.List;

import org.glorund.calc.ExpressionTree;
import org.glorund.calc.ExpressionValue;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.StatementOperator;

public class ExpressionStack {
    private ExpressionTree node;
    private final List<ExpressionValue> values; 
    public ExpressionStack() {
        super();
        values = new ArrayList<>();
    }
    
    public ExpressionTree getNode() {
        return node;
    }

    public List<ExpressionValue> getValues() {
        return values;
    }


    public boolean pushAsInitial(Operator operator, String operand) {
        if (node == null) {
            node = new ExpressionTree(operator);
            ValueToken token = parseValue(operand);
            if (!token.isConstant()) {
                values.add(token.getValue());
            }
            node.setLeftOperand(token.getValue());
            return true;
        }
        return false;
    }
    public boolean push(Operator operator, String operand) {
        if (operand.length() > 0 ) {
            ValueToken token = parseValue(operand);
            node.setRightOperand(token.getValue());
            if (!token.isConstant()) {
                values.add(token.getValue());
            }
        }
        ExpressionTree expr = new ExpressionTree(operator);
        expr.setLeftOperand(node);
        node = expr;
        return false;
    }

    //RC TODO make it private
    public ValueToken parseValue(String argument) {
        try{
            double doudleValue = Double.parseDouble(argument); 
            ExpressionValue value = new ExpressionValue("Const.");
            value.setValue(doudleValue);
            return new ValueToken(true, value);
        } catch (NumberFormatException e) {
            ExpressionValue value = new ExpressionValue(argument);
            return new ValueToken(false, value);
        }
    }

    public void push(Operator operator, ExpressionTree tree, List<ExpressionValue> values) {
        if (node == null) {
            node = new ExpressionTree(operator,tree);
        } else {
            node.setRightOperand(tree);
        }
        this.values.addAll(values);
    }

    public void push(ExpressionTree tree, List<ExpressionValue> values) {
        node.setRightOperand(tree);
        this.values.addAll(values);
    }

    public void push(String rigthOperand) {
        if (rigthOperand.length() > 0) {
            ValueToken token = parseValue(rigthOperand);
            if (node == null) {
                node = new ExpressionTree(new StatementOperator());
                node.setLeftOperand(token.getValue());
            } else {
                node.setRightOperand(token.getValue());
            }
            if (!token.isConstant()) {
                values.add(token.getValue());
            }
        }
    }

}
