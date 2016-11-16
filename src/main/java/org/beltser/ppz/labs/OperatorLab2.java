package org.beltser.ppz.labs;

import org.beltser.mathlab.Operator;
import org.beltser.mathlab.exception.ComputingTimeLimitException;
import org.beltser.mathlab.exception.ExpressionParsingException;
import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.ExpressionParser;
import org.beltser.mathlab.report.Report;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterConsole;
import org.beltser.mathlab.utils.MathUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OperatorLab2 extends Operator<BigDecimal> {

    private static final long DEFAULT_TIME_LIMIT = 10;
    private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

    private long timeLimit = DEFAULT_TIME_LIMIT;
    private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

    public static final String EXPRESSION_FIELD_NAME = "integrand";
    public static final String BEGIN_BOUNDARY_FIELD_NAME = "a";
    public static final String END_BOUNDARY_FIELD_NAME = "b";

    private ReportPrinter<BigDecimal> reportPrinter = new ReportPrinterConsole<>();

    public OperatorLab2(ReportPrinter reportPrinter) {
        this.reportPrinter = reportPrinter;
    }

    public OperatorLab2(long timeLimit, TimeUnit timeLimitTimeunit) {
        this.timeLimit = timeLimit;
        this.timeLimitTimeunit = timeLimitTimeunit;
    }

    protected Map<String, Object> getInput() {
        System.out.println("Please, enter expression: ");
        final String stringExpression = read("1/(x*sqr(1-log(x))");
        System.out.println("Enter integrating borders.");
        System.out.println("Enter a (bottom limit):");
        final double a = read(1);
        System.out.println("Enter b (upper limit):");
        final double b = read(Math.E);

        Map<String, Object> inputData = new HashMap<>();
        inputData.put(EXPRESSION_FIELD_NAME, stringExpression);
        inputData.put(BEGIN_BOUNDARY_FIELD_NAME, a);
        inputData.put(END_BOUNDARY_FIELD_NAME, b);
        return inputData;
    }


    protected BigDecimal compute(Map inputtedData) throws ComputingTimeLimitException {
        long beginTime = System.currentTimeMillis();
        String expressionString = (String) inputtedData.get(EXPRESSION_FIELD_NAME);
        double a = (double) inputtedData.get(BEGIN_BOUNDARY_FIELD_NAME);
        double b = (double) inputtedData.get(END_BOUNDARY_FIELD_NAME);

        Expression expression = null;
        try {
            expression = ExpressionParser.PARSER_INSTANCE.parse(expressionString);
        } catch (ExpressionParsingException e) {
            e.printStackTrace();
        }
        final double stepSize = 0.0001;
        final long stepsAmount = (long) Math.floor((b - a) / stepSize);

        BigDecimal integralValue = new BigDecimal(0);
        System.out.println("\nIterations amount: " + stepsAmount);
        System.out.println("Estimated time: " + Math.round((double) stepsAmount / 240_000) + " seconds.");
        System.out.println("Start calculations. . .");
        BigDecimal functionValueInCurrentPoint;
        for (int i = 0; i < stepsAmount; i++) {
            functionValueInCurrentPoint = BigDecimal.valueOf(expression.value(
                    (double) (a + i * stepSize + stepSize / 2d)
            ));
            integralValue = integralValue.add(
                    functionValueInCurrentPoint
            );
//            MathSystem.out.println(i + ". x = " + (a + i * stepSize + stepSize / 2d) + ", y = " + functionValueInCurrentPoint.doubleValue());
            if (System.currentTimeMillis() - beginTime > timeLimitTimeunit.toMillis(timeLimit)) {
                throw new ComputingTimeLimitException();
            }
        }
        integralValue = integralValue.multiply(new BigDecimal(stepSize));
        integralValue = MathUtil.round(integralValue, 10);
        return integralValue;
    }

    @Override
    protected void showResult(Report<BigDecimal> report) {
        reportPrinter.print(report);
    }

}
