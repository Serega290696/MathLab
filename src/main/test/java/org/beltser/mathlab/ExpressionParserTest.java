package org.beltser.mathlab;

import org.beltser.mathlab.expressions.types.NumericExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class ExpressionParserTest {
    private Map<String, Double> testExpressions;
    private Map<String, Double> testExpressionsWithVariables;
    private Map<Integer, NumericExpression> variables;

    @Before
    public void setUp() {
        testExpressions = new HashMap<>();
        testExpressionsWithVariables = new HashMap<>();
        variables = new HashMap<>();

        testExpressions.put("14-9.1+4", 14 - 9.1 + 4);
        testExpressions.put("5 + 2 * 5^(-2) - 2", 5 + 2 * pow(5, -2) - 2);
        testExpressions.put("(5^2 - cos(34)) *(-6.6) * e^(-2) + 2 * (2 + 2)",
                (pow(5, 2) - cos(34)) * (-6.6) * exp(-2) + 2 * (2 + 2));

//        testExpressionsWithVariables.put("2*x1-x2^2",
//                2 * x1 - pow(x2, 2));
//
//        variables.put(1, new NumericExpression(x1));
//        variables.put(2, new NumericExpression(x2));
        double x1 = 1;
        double x2 = 2;
        testExpressionsWithVariables.put("27*2^x1^2-cos(x2*4)-x1/x2",
                (pow(27*2,pow(x1,2))-cos(x2*4)-x1/x2));

        variables.put(1, new NumericExpression(-12.5));
        variables.put(2, new NumericExpression(256));
    }

    @Test
    public void testParse() throws Exception {
        for (String expression : testExpressions.keySet()) {
            System.out.println("Expression: " + expression);
            Assert.assertEquals(
                    testExpressions.get(expression),
                    ExpressionParser.PARSER_INSTANCE.parse(expression).value(), 0);
        }
    }

    @Test
    public void testParseVariables() throws Exception {
        for (String expression : testExpressionsWithVariables.keySet()) {
            double value = ExpressionParser.PARSER_INSTANCE
                    .parse(expression)
                    .replaceVariableBy(variables)
                    .value();
            Double aDouble = testExpressionsWithVariables.get(expression);
            Assert.assertEquals(
                    value,
                    aDouble, 0);
        }
    }
}