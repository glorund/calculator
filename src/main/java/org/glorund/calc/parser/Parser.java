package org.glorund.calc.parser;

import java.util.ArrayList;
import java.util.List;

import org.glorund.calc.Expression;
import org.glorund.calc.ExpressionTree;
import org.glorund.calc.ExpressionValue;
import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.AssignOperator;
import org.glorund.calc.operator.DivineOperator;
import org.glorund.calc.operator.MultiplyOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.SubtractionOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    
    private final List<Operator> operators = new ArrayList<>();
    
    public Parser() {
        super();
        operators.add(new AdditionOperator());
        operators.add(new SubtractionOperator());
        operators.add(new MultiplyOperator());
        operators.add(new DivineOperator());
    }

    public Expression parse(String formula) {
        LOGGER.debug("parsing formula");
        Expression result = parse(formula,false,0).getExpression();
        LOGGER.debug("complite. parsed resalt is {} {}",result.getTree(), result.getValues());
        return result;
    }

    private ExpressionToken parse(String formula,boolean inBrakets,int priority) {
        List<ExpressionValue> values = new ArrayList<>();
        int pos = 0;
        String rigthOperand = "";
        ExpressionTree leftOperator = null;
        while (pos < formula.length()) {
            if (inBrakets && formula.charAt(pos)== ')') {
                break;
            }
            if (formula.charAt(pos)== '(') {
                ExpressionToken internal = parse(formula.substring(pos+1),true,priority);
                pos+=internal.getIndex()+2;
                if (leftOperator == null) {
                    leftOperator = new ExpressionTree(new AssignOperator());
                    leftOperator.setLeftOperand(internal.getExpression().getTree());
                } else {
                    leftOperator.setRightOperand(internal.getExpression().getTree());
                }
                values.addAll(internal.getExpression().getValues());
            } else {
                OperatorToken operator = getNextOperator(formula, pos);
                int oldPos = pos;
                if (operator != null) {
                    String operand = formula.substring(pos, operator.getIndex());
                    pos+=operator.getIndex()+1;
                    if (leftOperator == null) {
                        leftOperator = new ExpressionTree(operator.getOperator());
                        ValueToken token = parseValue(operand);
                        if (!token.isConstant()) {
                            values.add(token.getValue());
                        }
                        leftOperator.setLeftOperand(token.getValue());
                    } else {
                        if (priority>operator.getOperator().getPriority()) {
                            rigthOperand = operand;
                            pos = oldPos + operand.length();
                            break;
                        }
                        if (priority<operator.getOperator().getPriority()) {
                            ExpressionToken internal = parse(formula.substring(oldPos),false, operator.getOperator().getPriority());
                            leftOperator.setRightOperand(internal.getExpression().getTree());
                            values.addAll(internal.getExpression().getValues());
                            pos=oldPos+internal.getIndex();
                        } else {
                            pos = operator.getIndex() + 1;
                            if (operand.length() > 0 ) {
                                ValueToken token = parseValue(operand);
                                leftOperator.setRightOperand(token.getValue());
                                if (!token.isConstant()) {
                                    values.add(token.getValue());
                                }
                            }
                            ExpressionTree expr = new ExpressionTree(operator.getOperator());
                            expr.setLeftOperand(leftOperator);
                            leftOperator = expr;
                        }
                    }
                } else {
                    rigthOperand += formula.charAt(pos);
                    pos++;
                }
            }
        }
        if (rigthOperand.length() > 0) {
            ValueToken token = parseValue(rigthOperand);
            leftOperator.setRightOperand(token.getValue());
            if (!token.isConstant()) {
                values.add(token.getValue());
            }
        }
        return new ExpressionToken(new Expression(leftOperator, values),pos);
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
    
    private OperatorToken getNextOperator(final String formula, final int start) {
        for (int charPos = start; charPos < formula.length(); charPos++) {
            if (formula.charAt(charPos) == ')') {
                return null;
            }
            for (Operator operator : operators) {
                if (formula.substring(charPos, charPos+1).equals(operator.getSymbol())) {
                    return new OperatorToken(operator, charPos);
                }
            }
        }
        return null;
    }

}
