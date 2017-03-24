package org.glorund.calc.operator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DivineOperatorTest {
    private static final double DELTA = 0.00000001;

    @Test
    public void evaluationTest() {
        BinaryOperator target =  new DivineOperator();
        double actual = target.evaluate(4., 5.);
        assertEquals(.8, actual,DELTA);
    }
}
