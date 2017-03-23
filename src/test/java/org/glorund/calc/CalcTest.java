package org.glorund.calc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.glorund.calc.parser.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalcTest {
    private static final double BASE_LINE = 7.3966803133452235;
    private static final double DELTA =  0.00000001;

    @InjectMocks
    private Parser parser;
    @InjectMocks
    private String formula = "X1+X2/(X3*6.1)-5";
    
    private Calc target;
    
    @Before
    public void setUp() {
        target = new Calc(parser,formula);
        target.init();
    }
    
    @Test
    public void baselineTest() {
        //[12.4,-0.2,98.765e-1]
        //X1+X2/(X3*6.1)-5
        double base  = 12.4+(-0.2/(98.765e-1*6.1))-5;
        assertEquals(BASE_LINE,base,DELTA);
    }
    
    @Test
    public void smoukeTest() {
        double actual = target.calculate(Arrays.asList(12.4,-0.2,98.765e-1));
        assertEquals(BASE_LINE,actual,DELTA);
    }
}
