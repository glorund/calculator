package org.glorund.calc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.glorund.calc.operator.AdditionOperator;
import org.glorund.calc.operator.Operator;
import org.glorund.calc.operator.SubtractionOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionTreeTest {
    private static final double DELTA = 0.00000001;

    @Mock
    private ExpressionNode leftOperand;

    @Mock
    private Operator operator;

    @Mock
    private ExpressionNode rightOperand;
    @InjectMocks
    private ExpressionTree target;

    @Test
    public void testEvaluate() throws Exception {
        List<ExpressionValue> values = new ArrayList<>();
        ExpressionValue value;
        ExpressionTree leaf = new ExpressionTree(new AdditionOperator());
        value = new ExpressionValue("X1");
        leaf.setLeftOperand(value);
        values.add(value);
        value = new ExpressionValue("X2");
        leaf.setRightOperand(value);
        values.add(value);
        ExpressionTree tree = new ExpressionTree(new SubtractionOperator());
        tree.setLeftOperand(leaf);
        value = new ExpressionValue("X3");
        tree.setRightOperand(value);
        values.add(value);

        Expression expr = new Expression(tree,values);
        
        List<Double> paramaters = Arrays.<Double>asList(12.4,42.,87.13);
        if (paramaters.size() == expr.getValues().size()) {
            int index = 0;
            for (double val : paramaters) {
                expr.getValues().get(index++).setValue(val);
            }
        }
        
        double actual = expr.getTree().evaluate();
        assertEquals(-32.73,actual,DELTA);
    }

}
