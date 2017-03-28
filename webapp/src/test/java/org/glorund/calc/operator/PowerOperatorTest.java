package org.glorund.calc.operator;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class PowerOperatorTest {
    Random random = new Random();
    private static final double DELTA = 0.00000001;
    @Test
    public void testEvaluate() throws Exception {
        double left = random.nextDouble();
        double right  = random.nextDouble();
        BinaryOperator target = new PowerOperator();
        double actual = target.evaluate(left, right);
        assertEquals(Math.pow(left, right),actual,DELTA);
    }

}
