package org.glorund.calc.parser;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.glorund.calc.Expression;
import org.glorund.calc.ExpressionTree;
import org.glorund.calc.ExpressionValue;
import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.DivineOperator;
import org.glorund.calc.operator.MultiplyOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.SubtractionOperator;

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

    public ExpressionToken parse(String formula,boolean inBrakets) {
        List<ExpressionValue> values = new ArrayList<>();
        int formulaLength = formula.length();
        int pos = 0;
        String rigthOperand = "";
        ExpressionTree leftOperator = null;
        while (pos < formulaLength) {
            if (inBrakets && formula.charAt(pos)== ')') {
                LOGGER.debug("braket closed. return");
                break;
            }
            if (formula.charAt(pos)== '(') {
                LOGGER.debug("braket foud at {}. Going deep",pos);
                ExpressionToken internal = parse(formula.substring(pos+1),true);
                pos+=internal.getIndex()+2;
                leftOperator.setRightOperand(internal.getExpression().getTree());
                values.addAll(internal.getExpression().getValues());
                LOGGER.debug("get back. {}", leftOperator);
            } else {
                OperatorToken operator = getNextOperator(formula, pos);
                if (operator != null) {
                    String operand = formula.substring(pos, operator.getIndex());
                    LOGGER.debug("operator is {} at {} left operand is {}", operator.getOperator().getSymbol(), operator.getIndex(),
                            operand);
                    // if ()
                    pos = operator.getIndex() + 1;
                    if (leftOperator == null) {
                        leftOperator = new ExpressionTree(operator.getOperator());
                        ExpressionValue value = new ExpressionValue(operand);
                        leftOperator.setLeftOperand(value);
                        values.add(value);
                        LOGGER.debug("unclosed {} {}",operand, operator.getOperator().getSymbol()); 
                    } else {
                        // looks great create node
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
                        LOGGER.debug("operator {} ",expr);
                    }
                } else {
                    rigthOperand += formula.charAt(pos);
                    pos++;
                }
            }
        }
        ValueToken token = parseValue(rigthOperand);
        leftOperator.setRightOperand(token.getValue());
        if (!token.isConstant()) {
            values.add(token.getValue());
        }
        LOGGER.debug("done {} {}", leftOperator,values);
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
            //lookup operators;
            for (Operator operator : operators) {
                if (formula.substring(charPos, charPos+1).equals(operator.getSymbol())) {
                    return new OperatorToken(operator, charPos);
                }
            }
        }
        return null;
    }
    
    @Deprecated
    public Expression oldParse(String formula) {
        List<ExpressionValue> values = new ArrayList<>();
        ExpressionValue value;
        ExpressionTree leaf = new ExpressionTree(new AdditionOperator());
        value = new ExpressionValue("X1");
        leaf.setLeftOperand(value);
        values.add(value);
        value = new ExpressionValue("X2");
        leaf.setRightOperand(value);
        values.add(value);
        ExpressionTree tree = new ExpressionTree(new SubtractionOperator());
        tree.setLeftOperand(leaf);
        value = new ExpressionValue("X3");
        tree.setRightOperand(value);
        values.add(value);
        
        return new Expression(tree,values);
    }

}
