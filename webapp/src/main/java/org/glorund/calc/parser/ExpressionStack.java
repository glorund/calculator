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


    public int push(OperatorToken operatorToken) {
        if (node == null) {
            return pushAsInitial(operatorToken);
        }
        return pushContinued(operatorToken);
    }

    public int pushAsInitial(OperatorToken operatorToken) {
        node = new ExpressionTree(operatorToken.getOperator());
        ValueToken token = parseValue(operatorToken.getOperand());
        addToValues(token);
        node.setLeftOperand(token.getValue());
        return operatorToken.getOperatorIndex()+1;
    }

    public int pushContinued(OperatorToken operatorToken) {
        if (operatorToken.getOperand().length() > 0 ) {
            ValueToken token = parseValue(operatorToken.getOperand());
            node.setRightOperand(token.getValue());
            addToValues(token);
        }
        ExpressionTree expr = new ExpressionTree(operatorToken.getOperator());
        expr.setLeftOperand(node);
        node = expr;
        return operatorToken.getOperatorIndex() + 1;
    }

    private ValueToken parseValue(String argument) {
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

    public int push(Operator operator, ExpressionToken token) {
        if (node == null) {
            node = new ExpressionTree(operator,token.getExpression().getTree());
        } else {
            node.setRightOperand(token.getExpression().getTree());
        }
        this.values.addAll(token.getExpression().getValues());
        return token.getIndex()+1;
    }

    public int push(ExpressionToken token) {
        node.setRightOperand(token.getExpression().getTree());
        this.values.addAll(token.getExpression().getValues());
        return token.getIndex();
    }

    public int push(String rigthOperand) {
        if (rigthOperand.length() > 0) {
            ValueToken token = parseValue(rigthOperand);
            if (node == null) {
                node = new ExpressionTree(new StatementOperator());
                node.setLeftOperand(token.getValue());
            } else {
                node.setRightOperand(token.getValue());
            }
            addToValues(token);
        }
        return rigthOperand.length();
    }

    private void addToValues(ValueToken token) {
        if (!token.isConstant()) {
            values.add(token.getValue());
        }
    }
    
    public boolean hasLowerPriorityThan(Operator operator, int basePriority) {
        return getNode() != null && getMaxPriority(getNode(),basePriority)<operator.getPriority();
    }

    public boolean hasHigherPriorityThan(Operator operator, int basePriority) {
        return getMinPriority(getNode(),basePriority)>operator.getPriority();
    }

    private int getMinPriority(ExpressionTree leftOperator, int priority) {
        return leftOperator == null ? priority : Math.min(priority, leftOperator.getOperator().getPriority());
    }

    
    private int getMaxPriority(ExpressionTree leftOperator, int priority) {
        return leftOperator == null ? priority : Math.max(priority, leftOperator.getOperator().getPriority());
    }
    
}
