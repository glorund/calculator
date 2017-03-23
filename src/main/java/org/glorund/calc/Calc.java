package org.glorund.calc;

import java.util.List;

import org.glorund.calc.parser.ExpressionToken;
import org.glorund.calc.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Calc {
    
    @Autowired
    private Parser parser;
    private final String formula = "X1+X2/(X3*6.1)-5";

    @RequestMapping("/calc") //(method=POST)
    public double greeting(@RequestBody List<Double> args) {
        
        Expression expression = parser.parse(formula,false).getExpression();

        if (args.size() == expression.getValues().size()) {
            int index = 0;
            for (double val : args) {
                expression.getValues().get(index++).setValue(val);
            }
        }
        return expression.getTree().evaluate();
    }
}
