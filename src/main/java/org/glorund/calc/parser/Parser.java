package org.glorund.calc.parser;

import java.util.ArrayList;
import java.util.List;

import org.glorund.calc.Expression;
import org.glorund.calc.ExpressionTree;
import org.glorund.calc.ExpressionValue;
import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.DivineOperator;
import org.glorund.calc.operator.MultiplyOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.StatementOperator;
import org.glorund.calc.operator.SubtractionOperator;
import org.glorund.calc.operator.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    
    private final List<Operator> operators = new ArrayList<>();
    
    public Parser() {
        super();
        operators.add(new StatementOperator());
        operators.add(new AdditionOperator());
        operators.add(new SubtractionOperator());
        operators.add(new MultiplyOperator());
        operators.add(new DivineOperator());
    }

    public Expression parse(String formula) throws ParsingException {
        Expression result = parseRecursor(formula,0).getExpression();
        LOGGER.debug("all complite. parsed resalt is {} {}",result.getTree(), result.getValues());
        return result;
    }

    private ExpressionToken parseRecursor(String formula, int priority) throws ParsingException {
        LOGGER.debug("parsing formula {}",formula);
        List<ExpressionValue> values = new ArrayList<>();
        int pos = 0;
        String rigthOperand = "";
        String formulaTail = formula;
        ExpressionTree leftOperator = null;
        ExpressionStack stack = new ExpressionStack();
        while (pos < formulaTail.length()) {
            String tail = formulaTail.substring(pos);
            OperatorToken operator = getNextOperator(formulaTail, pos);
            if (operator == null) { // only right operand remains
                rigthOperand = tail;
                pos=formulaTail.length();
                break;
            }
            int oldPos = pos;
            String operand = formulaTail.substring(pos, operator.getIndex());
            if (operator.getOperator().isUnary()) {
                if (operand.length()!=0) {
                    throw new ParsingException(
                            "Syntax error. left operand found unary "+operator.getOperator().getSymbol()
                            +" at pos "+ pos + " expression: "+formulaTail);
                }
                int closedPos = findMatched(formulaTail.substring(pos), (UnaryOperator)operator.getOperator());
                if (closedPos < 0) {
                    throw new ParsingException("Closed symbol not found for opened expression at pos "+pos+" expression: " + formulaTail);
                }
                ExpressionToken internal = parseRecursor(tail.substring(1,closedPos),0);
                pos+=closedPos+1;
                stack.push(operator.getOperator(),internal.getExpression().getTree(), internal.getExpression().getValues());
                if (leftOperator == null) {
                    leftOperator = new ExpressionTree(operator.getOperator(),internal.getExpression().getTree());
                } else {
                    leftOperator.setRightOperand(internal.getExpression().getTree());
                }
                values.addAll(internal.getExpression().getValues());
            } else {
                if (operand.length()==0 && !valid(leftOperator) ) {
                    throw new ParsingException(
                            "Syntax error. left operand not found for "+operator.getOperator().getSymbol()
                            +" at pos "+ pos + " expression: "+formulaTail);
                }
                pos+=operator.getIndex()+1;
                stack.push(true,operator.getOperator(),operand);
                if (leftOperator == null) {
                    leftOperator = new ExpressionTree(operator.getOperator());
                    ValueToken token = parseValue(operand);
                    if (!token.isConstant()) {
                        values.add(token.getValue());
                    }
                    leftOperator.setLeftOperand(token.getValue());
                } else {
                    if (getMinPriority(leftOperator,priority)>operator.getOperator().getPriority()) {
                        rigthOperand = operand;
                        pos = oldPos + operand.length();
                        break;
                    }
                    if (getMaxPriority(leftOperator,priority)<operator.getOperator().getPriority()) {
                        ExpressionToken internal = parseRecursor(formulaTail.substring(oldPos),operator.getOperator().getPriority());
                        stack.push(internal.getExpression().getTree(), internal.getExpression().getValues());
                        leftOperator.setRightOperand(internal.getExpression().getTree());
                        values.addAll(internal.getExpression().getValues());
                        pos=oldPos+internal.getIndex();
                    } else {
                        pos = operator.getIndex() + 1;
                        stack.push(false,operator.getOperator(),operand);
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
            }
        }
        stack.push(rigthOperand);
        if (rigthOperand.length() > 0) {
            ValueToken token = parseValue(rigthOperand);
            if (leftOperator == null) {
                leftOperator = new ExpressionTree(new StatementOperator());
                leftOperator.setLeftOperand(token.getValue());
            } else {
                leftOperator.setRightOperand(token.getValue());
            }
            if (!token.isConstant()) {
                values.add(token.getValue());
            }
        }
        LOGGER.debug("Done {}",stack.getNode());
        return new ExpressionToken(new Expression(stack.getNode(), stack.getValues()),pos);
    }


    private int getMaxPriority(ExpressionTree leftOperator, int priority) {
        return leftOperator == null ? priority : Math.max(priority, leftOperator.getOperator().getPriority());
    }
    
    private int getMinPriority(ExpressionTree leftOperator, int priority) {
        return leftOperator == null ? priority : Math.min(priority, leftOperator.getOperator().getPriority());
    }

    private boolean valid(ExpressionTree leftOperator) {
        return leftOperator != null && leftOperator.isValid();
    }

    @Deprecated
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
            for (Operator operator : operators) {
                if (formula.charAt(charPos) == operator.getSymbol()) {
                    return new OperatorToken(operator, charPos);
                }
            }
        }
        return null;
    }

    private int findMatched(final String formula,final UnaryOperator operator) throws ParsingException {
        int count = 0; 
        for (int charPos = 0;charPos < formula.length(); charPos++) {
            if (formula.charAt(charPos) == operator.getSymbol() ) {
                count++;
            }
            if (formula.charAt(charPos) == operator.getClosedChar()) {
                count--;
            }
            if (count < 1) {
                return charPos;
            }
        } 
        throw new ParsingException("Closed symbol not found for expression: " + formula);
    }
}
