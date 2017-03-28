package org.glorund.calc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.glorund.calc.parser.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalcTest {
    private static final double DELTA =  0.00000001;
    Random random = new Random();

    @InjectMocks
    private Parser parser;

    @Test
    public void smoukeTest() throws Exception {
        double X1 = random.nextDouble();
        double X2 = random.nextDouble();
        double X3 = random.nextDouble();

        double base  = X1+X2/(X3*6.1)-5;
        Calc target = new Calc(parser,"X1+X2/(X3*6.1)-5");
        target.init();

        double actual = target.calculate(Arrays.asList(X1,X2,X3));
        assertEquals(base,actual,DELTA);
    }
    
    @Test
    public void smoukeSecondTest() throws Exception {
        double X1 = random.nextDouble();
        double X2 = random.nextDouble();
        double X3 = random.nextDouble();
        double base = (X1+X2)/(X3*6.1)-5;
        Calc target = new Calc(parser,"(X1+X2)/(X3*6.1)-5");
        target.init();

        double actual = target.calculate(Arrays.asList(X1,X2,X3));
        assertEquals(base,actual,DELTA);
    }
    
    @Test
    public void smoukePowerTest() throws Exception {
        double X1 = random.nextDouble();
        double X2 = random.nextDouble()*2-1;
        double X3 = random.nextDouble();
        double base = Math.pow(X1,X2)/(X3*6.1)-5;
        Calc target = new Calc(parser,"X1^X2/(X3*6.1)-5");
        target.init();

        double actual = target.calculate(Arrays.asList(X1,X2,X3));
        assertEquals(base,actual,DELTA);
    }
}
