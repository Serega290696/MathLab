package org.beltser.ppz.labs;

import org.beltser.mathlab.Operator;
import org.beltser.mathlab.exception.TimeLimitException;
import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.parser.ExpressionParser;
import org.beltser.mathlab.report.Report;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterConsole;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OperatorLab3 extends Operator<BigDecimal> {

    private static final long DEFAULT_TIME_LIMIT = 5;
    private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

    private long timeLimit = DEFAULT_TIME_LIMIT;
    private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

    public static final String EXPRESSION_FIELD_NAME = "expression";
    public static final String X_ZERO_FIELD_NAME = "x0";
    public static final String Y_ZERO_FIELD_NAME = "y0";
    public static final String A_FIELD_NAME = "a";
    public static final String B_FIELD_NAME = "b";

    private ReportPrinter<BigDecimal> reportPrinter = new ReportPrinterConsole<>();

    public OperatorLab3(ReportPrinter reportPrinter) {
        this.reportPrinter = reportPrinter;
    }

    public OperatorLab3(long timeLimit, TimeUnit timeLimitTimeunit) {
        this.timeLimit = timeLimit;
        this.timeLimitTimeunit = timeLimitTimeunit;
    }

    protected Map getInput() {
        System.out.println("Please, enter expression: ");
        final String stringExpression = read("0.5*x*y^2-y");//x+y
        System.out.println("Enter x0: ");
        final double x0 = read(0);
        System.out.println("Enter y0: ");
        final double y0 = read(2);
        System.out.println("Enter a: ");
        final double a = read(0);
        System.out.println("Enter b: ");
        final double b = read(2);

        Map<String, Object> inputData = new HashMap<>();
        inputData.put(EXPRESSION_FIELD_NAME, stringExpression);
        inputData.put(X_ZERO_FIELD_NAME, x0);
        inputData.put(Y_ZERO_FIELD_NAME, y0);
        inputData.put(A_FIELD_NAME, a);
        inputData.put(B_FIELD_NAME, b);
        return inputData;
    }


    protected BigDecimal compute(Map inputtedData) throws TimeLimitException {
        long beginTime = System.currentTimeMillis();
        String expressionString = (String) inputtedData.get(EXPRESSION_FIELD_NAME);
        double x = (double) inputtedData.get(X_ZERO_FIELD_NAME);
        double y = BigDecimal.valueOf((double) inputtedData.get(Y_ZERO_FIELD_NAME)).doubleValue();
        double a = (double) inputtedData.get(A_FIELD_NAME);
        double b = (double) inputtedData.get(B_FIELD_NAME);

        Expression expression = ExpressionParser.parse(expressionString);

        final double h = 0.001; // step size
        final double epsilon = Math.pow(10d, -5);

//        MathSystem.out.println("Estimated time: " + Math.round((double) stepsAmount / 240_000) + " seconds.");
        System.out.println("Start calculations. . .");
        double k1, k2, k3, k4, prevY;
//        BigDecimal k1, k2, k3, prevY;
        int counter = 0;
        x = a;
        do {
            counter++;
            prevY = y;
            k1 = expression.value(x, y);
            k2 = expression.value(x + h / 2, y + h / 2 * k1);
            k3 = expression.value(x + h / 2, y + h / 2 * k2);
            k4 = expression.value(x + h, y + h * k3);
            y += h / 6d * (k1 + 2d * k2 + 2d * k3 + k4);
            x += h;
//            k1 = BigDecimal.valueOf(expression.value(x, y.doubleValue()));
//            k2 = BigDecimal.valueOf(
//                    expression.value(
//                            x + h / 2,
//                            k1
//                                    .multiply(BigDecimal.valueOf(h / 2))
//                                    .add(BigDecimal.valueOf(y.doubleValue()))
//                                    .doubleValue()
//                    )
//            );
//            k3 = BigDecimal.valueOf(
//                    expression.value(
//                            x + h / 2,
//                            k1
//                                    .multiply(BigDecimal.valueOf(h / 2))
//                                    .add(BigDecimal.valueOf(y.doubleValue()))
//                                    .doubleValue()
//                    )
//            );
//            y = prevY
//                    .add(
//                            k1
//                                    .add(k2.multiply(BigDecimal.valueOf(2)))
//                                    .add(k3.multiply(BigDecimal.valueOf(2)))
//                                    .multiply(BigDecimal.valueOf(h))
//                                    .divide(BigDecimal.valueOf(6), BigDecimal.ROUND_HALF_UP)
//                    );
//            MathSystem.out.println(i + ". x = " + (a + i * stepSize + stepSize / 2d) + ", y = " + functionValueInCurrentPoint.doubleValue());
            if (counter % 100 == 0) {
                System.out.println("x = " + x + ". y = " + y + ".");
            }
            if (System.currentTimeMillis() - beginTime > timeLimitTimeunit.toMillis(timeLimit)) {
                throw new TimeLimitException();
            }
        } while (x < b);
        System.out.println(y);
//        integralValue = integralValue.multiply(new BigDecimal(stepSize));
//        integralValue = MathUtil.round(integralValue, 10);
        return BigDecimal.valueOf(y);
    }

    @Override
    protected void showResult(Report<BigDecimal> report) {
        reportPrinter.print(report);
    }

}
