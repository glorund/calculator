package org.glorund.calc;

import java.util.List;

import javax.annotation.PostConstruct;

import org.glorund.calc.parser.Parser;
import org.glorund.calc.parser.ParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Calc {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calc.class);

    private Parser parser;
    private String formula;

    Expression expression;
    
    
    @Autowired
    public Calc(Parser parser,@Value("${formula}") String formula) {
        super();
        this.parser = parser;
        this.formula = formula;
    }

    @PostConstruct
    public void init() throws ParsingException {
        expression = parser.parse(formula);
    }

    @RequestMapping("/calc") //(method=POST)
    public double calculate(@RequestBody List<Double> args) {
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
