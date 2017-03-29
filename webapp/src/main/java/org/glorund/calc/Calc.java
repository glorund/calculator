package org.glorund.calc;

import java.util.List;

import javax.annotation.PostConstruct;

import org.glorund.calc.parser.Parser;
import org.glorund.calc.parser.ParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @RequestMapping(value="/config")
    public String getFormula() {
        return formula;
    }
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public String calculateTest(@RequestBody List<String> params) {
        return "Got "+ params;
    }
    
    @RequestMapping(value="/calc", method = RequestMethod.POST, consumes = "application/json") //(method=POST)
    public double calculate(@RequestBody List<Double> args) {
        if (args.size() == expression.getValues().size()) {
            int index = 0;
            for (double val : args) {
                expression.getValues().get(index++).setValue(val);
            }
        } else {
            throw new IllegalArgumentException("invalid number of parameters. Expecting "+ expression.getValues().size() + " got "+ args.size());
        }
        LOGGER.debug("all set {} {}", expression.getTree(),expression.getValues());
        return expression.getTree().evaluate();
    }
    
    @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE,reason="Invalid Arguments")
    @ExceptionHandler(IllegalArgumentException.class)
    public void errorHandler() {
        // Nothing to do
    }
}
