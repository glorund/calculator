package org.glorund.calc.operator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AdditionOperatorTest {
    private static final double DELTA = 0.00000001;

    @Test
    public void evaluationTest() {
        Operator target =  new AdditionOperator();
        double actual = target.evaluate(4., 5.);
        assertEquals(9., actual,DELTA);
    }
}
