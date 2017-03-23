/**
 * 
 */
package org.glorund.calc.parser;

import static org.junit.Assert.assertEquals;

import org.glorund.calc.Expression;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Roman_Cherednichenko
 *
 */
public class ParserTest {
    @Test
    public void parseTest() {
        Parser target = new Parser();
        String formula = "X1+X2/(X3*6.1)-5";
        Expression actual = target.parse(formula);
        assertEquals("(({X1=0.0}+({X2=0.0}/({X3=0.0}*{Const.=6.1})))-{Const.=5.0})",actual.getTree().toString());
        assertEquals("[{X1=0.0}, {X2=0.0}, {X3=0.0}]",actual.getValues().toString());
    }
    @Test
    public void parseSecondTest() {
        Parser target = new Parser();
        String formula = "X1+X2/(X3-6.1)-5";
        Expression actual = target.parse(formula);
        assertEquals("(({X1=0.0}+({X2=0.0}/({X3=0.0}-{Const.=6.1})))-{Const.=5.0})",actual.getTree().toString());
        assertEquals("[{X1=0.0}, {X2=0.0}, {X3=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseSimpleTest() {
        Parser target = new Parser();
        String formula = "a+b-c";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}+{b=0.0})-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parseBracesTest() {
        Parser target = new Parser();
        String formula = "(a+b)-c";
        Expression actual = target.parse(formula);
        assertEquals("(=(({a=0.0}+{b=0.0}))-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    @Test
    public void parseBracesTailTest() {
        Parser target = new Parser();
        String formula = "a+(b-c)";
        Expression actual = target.parse(formula);
        assertEquals("({a=0.0}+({b=0.0}-{c=0.0}))",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }

    @Test
    public void parseSimplePriorityTest() {
        Parser target = new Parser();
        String formula = "a*b-c";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}*{b=0.0})-{c=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parsePriorityTest() {
        Parser target = new Parser();
        String formula = "a+b/c";
        Expression actual = target.parse(formula);
        assertEquals("({a=0.0}+({b=0.0}/{c=0.0}))",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}]",actual.getValues().toString());
    }
    
    @Test
    public void parsePriorityFourTest() {
        Parser target = new Parser();
        String formula = "a+b/c-d";
        Expression actual = target.parse(formula);
        assertEquals("(({a=0.0}+({b=0.0}/{c=0.0}))-{d=0.0})",actual.getTree().toString());
        assertEquals("[{a=0.0}, {b=0.0}, {c=0.0}, {d=0.0}]",actual.getValues().toString());
    }

}
