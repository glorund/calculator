package org.glorund.calc.processor.parser;

public class ParsingException extends Exception {
    private static final long serialVersionUID = 1674809746293714261L;
    private static final String SYNTAX_ERROR = "Syntax Error: ";
    private final String message;

    public ParsingException(String message, String formula, int index) {
        super();
        this.message = 
                (new StringBuilder(SYNTAX_ERROR))
                .append(message)
                .append(" pos ")
                .append(index)
                .append(" for expression: ")
                .append(formula)
                .toString();
    }
    @Override
    public String getMessage() {
        return message;
    }

}
