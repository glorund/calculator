package org.glorund.calc.operator;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class AdditionOperatorTest {
    private static final double DELTA = 0.00000001;
    Random random = new Random();
    @Test
    public void evaluationTest() {
        double left = random.nextDouble();
        double right  = random.nextDouble();
        BinaryOperator target =  new AdditionOperator();
        double actual = target.evaluate(left,right);
        assertEquals(left+right, actual,DELTA);
    }
}
