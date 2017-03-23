package org.glorund.calc;

import java.util.List;

import org.glorund.calc.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Calc {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calc.class);

    @Autowired
    private Parser parser;
    private final String formula = "X1+X2/(X3*6.1)-5";

    @RequestMapping("/calc") //(method=POST)
    public double calculate(@RequestBody List<Double> args) {
        
        Expression expression = parser.parse(formula,false,0).getExpression();

        if (args.size() == expression.getValues().size()) {
            int index = 0;
            for (double val : args) {
                expression.getValues().get(index++).setValue(val);
            }
        }
        LOGGER.debug("all set {} {}", expression.getTree(),expression.getValues());
        return expression.getTree().evaluate();
    }
}
