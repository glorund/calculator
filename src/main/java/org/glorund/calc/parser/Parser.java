package org.glorund.calc.parser;

import java.util.ArrayList;
import java.util.List;

import org.glorund.calc.Expression;
import org.glorund.calc.ExpressionTree;
import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.DivineOperator;
import org.glorund.calc.operator.MultiplyOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.PowerOperator;
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
        operators.add(new PowerOperator());
    }

    public Expression parse(String formula) throws ParsingException {
        Expression result = parseRecursor(formula,0).getExpression();
        LOGGER.debug("all complite. parsed resalt is {} {}",result.getTree(), result.getValues());
        return result;
    }

    private ExpressionToken parseRecursor(String formula, int priority) throws ParsingException {
        LOGGER.debug("parsing formula {}",formula);
        ExpressionStack stack = new ExpressionStack();
        OperatorToken operator = new OperatorToken(null, null, formula, 0, 0);
        for (operator = iterate(operator); !operator.complete() ; operator = iterate(operator)) {
            if (operator.getOperator().isUnary()) {
                ExpressionToken internal = processUnaryOperator(operator, formula, operator.getFormula().substring(operator.getOperatorIndex()));
                operator.pushTail(operator.getOperatorIndex()+stack.push(operator.getOperator(),internal) + 1);
                continue;
            }
            if (operator.getOperand().length()==0 && !valid(stack.getNode()) ) {
                throw new ParsingException(
                        "left operand not found for "+operator.getOperator().getSymbol(), formula, operator.getOperatorIndex());
            }
            if (stack.hasHigherPriorityThan(operator.getOperator(),priority)) {
                operator = new OperatorToken(null, operator.getOperand(), operator.getFormula(), operator.getOperatorIndex(),operator.getOperatorIndex());
                break;
            }
            if (stack.hasLowerPriorityThan(operator.getOperator(), priority)) { // next operand is has bigger priority
                ExpressionToken internal = parseRecursor(operator.getFormula().substring(operator.getOperatorIndex()-operator.getOperand().length()),priority+1);
                operator.pushTail(operator.getOperatorIndex()-operator.getOperand().length()+stack.push(internal));
                continue;
            }
            operator.pushTail(stack.push(operator));
        }
        operator.pushTail(stack.push(operator.getOperand()));
        LOGGER.debug("Done {} ",stack.getNode());
        return new ExpressionToken(new Expression(stack.getNode(), stack.getValues()),operator.getIndex());
    }

    private ExpressionToken processUnaryOperator(OperatorToken operator, String formula, String tail) throws ParsingException {
        if (operator.getOperand().length()!=0) {
            throw new ParsingException(
                    "left operand found unary "+operator.getOperator().getSymbol(), formula,operator.getOperatorIndex());
        }
        int closedPos = findMatched(tail, (UnaryOperator)operator.getOperator());
        return parseRecursor(tail.substring(1,closedPos),0);
    }

    private boolean valid(ExpressionTree leftOperator) {
        return leftOperator != null && leftOperator.isValid();
    }

    private OperatorToken iterate(OperatorToken token) {
        for (int charPos = token.getTailIndex(); charPos < token.getFormula().length(); charPos++) {
            for (Operator operator : operators) {
                if (token.getFormula().charAt(charPos) == operator.getSymbol()) {
                    return new OperatorToken(operator,token.getFormula().substring(token.getTailIndex(),charPos), token.getFormula() ,charPos,charPos);
                }
            }
        }
        return new OperatorToken(null,token.getFormula().substring(token.getTailIndex()) ,token.getFormula(), token.getIndex(),token.getFormula().length());
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
        throw new ParsingException("Closed symbol not found",formula,0);
    }
}
