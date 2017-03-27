/**
 * 
 */
package org.glorund.calc.parser;

import static org.junit.Assert.assertEquals;

import org.glorund.calc.Expression;
import org.junit.Test;

/**
 * @author Roman_Cherednichenko
 *
 */
public class ParserTest {
    @Test
    public void parseTest() throws Exception {
        Parser target = new Parser();
        String formula = "X1+X2/(X3*6.1)-5";
        Expression actual = target.parse(formula);
        assertEquals("(({X1=0.0}+({X2=0.0}/({X3=0.0}*{Const.=6.1})))-{Const.=5.0})",actual.getTree().toString());
        assertEquals("[{X1=0.0}, {X2=0.0}, {X3=0.0}]",actual.getValues().toString());
    }
    @Test
    public void parseSecondTest() throws Exception {
        Parser target = new Parser();
        String formula = "X1+X2/(X3-6.1)-5";
        Expression actual = target.parse(formula);
        assertEquals("(({X1=0.0}+({X2=0.0}/({X3=0.0}-{Const.=6.1})))-{Const.=5.0})",actual.getTree().toString());
        assertEquals("[{X1=0.0}, {X2=0.0}, {X3=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseSimpleTest() throws Exception {
        Parser target = new Parser();
        String formula = "a+b-c";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}+{b=0.0})-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parseBracesTest() throws Exception {
        Parser target = new Parser();
        String formula = "(a+b)-c";
        Expression actual = target.parse(formula);
        assertEquals("((({a=0.0}+{b=0.0}))-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseConstantTest() throws Exception {
        Parser target = new Parser();
        String formula = "(12.4+b)-c";
        Expression actual = target.parse(formula);
        assertEquals("((({Const.=12.4}+{b=0.0}))-{c=0.0})",actual.getTree().toString());
        assertEquals("[{b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    @Test
    public void parseIntenalConstantTest() throws Exception {
        Parser target = new Parser();
        String formula = "(a+56.3)-45.3";
        Expression actual = target.parse(formula);
        assertEquals("((({a=0.0}+{Const.=56.3}))-{Const.=45.3})",actual.getTree().toString());
        assertEquals("[{a=0.0}]",actual.getValues().toString());
    }

    
    @Test
    public void parseBracesTailTest() throws Exception {
        Parser target = new Parser();
        String formula = "a+(b-c)";
        Expression actual = target.parse(formula);
        assertEquals("({a=0.0}+({b=0.0}-{c=0.0}))",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseSimplePriorityTest() throws ParsingException {
        Parser target = new Parser();
        String formula = "a*b-c";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}*{b=0.0})-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parsePriorityTest() throws Exception {
        Parser target = new Parser();
        String formula = "a+b/c";
        Expression actual = target.parse(formula);
        assertEquals("({a=0.0}+({b=0.0}/{c=0.0}))",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parsePriorityFourTest() throws Exception {
        Parser target = new Parser();
        String formula = "a+b/c-d";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}+({b=0.0}/{c=0.0}))-{d=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}, {d=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseStartingBracesTest() throws Exception {
        Parser target = new Parser();
        String formula = "(a+b)/c-d";
        Expression actual = target.parse(formula);
        assertEquals("(((({a=0.0}+{b=0.0}))/{c=0.0})-{d=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}, {d=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parseBothBracesTest() throws Exception {
        Parser target = new Parser();
        String formula = "(a+b)/(c-d)";
        Expression actual = target.parse(formula);
        assertEquals("((({a=0.0}+{b=0.0}))/({c=0.0}-{d=0.0}))",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}, {d=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseSingleBracesTest() throws Exception {
        Parser target = new Parser();
        String formula = "(a+b)/(c)-d";
        Expression actual = target.parse(formula);
        assertEquals("(((({a=0.0}+{b=0.0}))/({c=0.0}))-{d=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}, {d=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parseErrorUnclosedBraketTest() throws Exception {
        Parser target = new Parser();
        String formula = "a+(b/(c)-d";
        try {
            target.parse(formula);
        } catch (ParsingException e) {
            assertEquals("Closed symbol not found for expression: (b/(c)-d", e.getMessage());
        }
    }

    @Test
    public void parseSyntaxErrorTest() throws Exception {
        Parser target = new Parser();
        String formula = "a*/b/c-d";
        try {
            target.parse(formula);
        } catch (ParsingException e) {
            assertEquals("Syntax error. left operand not found for / at pos 2 expression: a*/b/c-d", e.getMessage());
        }
    }
}
