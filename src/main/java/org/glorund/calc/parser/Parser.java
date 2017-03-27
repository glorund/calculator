package org.glorund.calc.parser;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.glorund.calc.Expression;
import org.glorund.calc.ExpressionTree;
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
        int pos = 0;
        int index = 0;
        String rigthOperand = "";
        ExpressionStack stack = new ExpressionStack();
        OperatorToken operator = getNextOperator(formula);
        for (; operator.getOperand() != null ;operator = getNextOperator(operator.getTail()), index+=pos) {
            String tail = operator.getTail();
            if (operator.getOperator() == null) { // only right operand remains
                rigthOperand = operator.getOperand();
                break;
            }
            if (operator.getOperator().isUnary()) {
                ExpressionToken internal = processUnaryOperator(operator, formula, tail);
                pos = stack.push(operator.getOperator(),internal) + 1;
                operator.pushTail(pos);
                continue;
            }
            if (operator.getOperand().length()==0 && !valid(stack.getNode()) ) {
                throw new ParsingException(
                        "Syntax error. left operand not found for "+operator.getOperator().getSymbol()
                        +" at pos "+ index + " expression: "+formula);
            }
            if (stack.hasHigherPriorityThan(operator.getOperator(),priority)) {
                rigthOperand = operator.getOperand();
                operator = new OperatorToken(null, operator.getOperand(), operator.getTail(), operator.getIndex());
                break;
            }
            if (stack.hasLowerPriorityThan(operator.getOperator(), priority)) { // next operand is has bigger priority
                ExpressionToken internal = parseRecursor(tail,operator.getOperator().getPriority());
                pos = stack.push(internal);
                operator.pushTail(pos);
                continue;
            }
            pos = stack.push(operator);
            operator.pushTail(pos);
        }
        index += stack.push(rigthOperand);
        operator.pushTail(rigthOperand.length());
        LOGGER.debug("Done {} ",stack.getNode());
        return new ExpressionToken(new Expression(stack.getNode(), stack.getValues()),index);
    }

    private ExpressionToken processUnaryOperator(OperatorToken operator, String formula, String tail) throws ParsingException {
        if (operator.getOperand().length()!=0) {
            throw new ParsingException(
                    "Syntax error. left operand found unary "+operator.getOperator().getSymbol()
                    +" at pos "+ operator.getIndex() + " expression: "+formula);
        }
        int closedPos = findMatched(tail, (UnaryOperator)operator.getOperator());
        if (closedPos < 0) {
            throw new ParsingException("Closed symbol not found for opened expression at pos "+operator.getIndex()+" expression: " + formula);
        }
        return parseRecursor(tail.substring(1,closedPos),0);
    }

    private boolean valid(ExpressionTree leftOperator) {
        return leftOperator != null && leftOperator.isValid();
    }

    private OperatorToken getNextOperator(final String formula) {
        for (int charPos = 0; charPos < formula.length(); charPos++) {
            for (Operator operator : operators) {
                if (formula.charAt(charPos) == operator.getSymbol()) {
                    return new OperatorToken(operator,formula.substring(0,charPos), formula ,charPos);
                }
            }
        }
        return new OperatorToken(null,formula ,formula,formula.length());
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
