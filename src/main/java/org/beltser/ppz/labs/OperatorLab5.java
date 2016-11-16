package org.beltser.ppz.labs;

import org.beltser.mathlab.Operator;
import org.beltser.mathlab.exception.TimeLimitException;
import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.linear_geometry.Point;
import org.beltser.mathlab.matrix.Matrix;
import org.beltser.mathlab.report.Report;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterConsole;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OperatorLab5 extends Operator<BigDecimal> {

  private static final long DEFAULT_TIME_LIMIT = 5;
  private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

  private long timeLimit = DEFAULT_TIME_LIMIT;
  private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

  public static final String FUNCTION_FIELD_NAME = "function";
  public static final String A_FIELD_NAME = "a";
  public static final String B_FIELD_NAME = "b";
  public static final String C_FIELD_NAME = "c";

  private ReportPrinter<BigDecimal> reportPrinter = new ReportPrinterConsole<>();

  public OperatorLab5(ReportPrinter reportPrinter) {
    this.reportPrinter = reportPrinter;
  }

  public OperatorLab5(long timeLimit, TimeUnit timeLimitTimeunit) {
    this.timeLimit = timeLimit;
    this.timeLimitTimeunit = timeLimitTimeunit;
  }

  protected Map getInput() {
    return null;
  }


  protected BigDecimal compute(Map inputtedData) throws TimeLimitException {
    final boolean DEBUG = true;
    //read input
    long beginTime = System.currentTimeMillis();
    Matrix a;
    Matrix b;
    Matrix c;
    if (DEBUG) {
      a = new Matrix(new double[][] {{6, 3, 1, 4}, {2, 4, 5, 1}, {1, 2, 4, 3}});
      b = new Matrix(new double[][] {{252}, {144}, {80}});
      c = new Matrix(new double[][] {{48}, {33}, {16}, {22}});
    } else {
      a = new Matrix((double[][]) inputtedData.get(A_FIELD_NAME));
      b = new Matrix((double[][]) inputtedData.get(B_FIELD_NAME));
      c = new Matrix((double[][]) inputtedData.get(C_FIELD_NAME));
    }

    //start algorithm
    System.out.println("Start calculations. . .");
    //init vars
    double[][] table = new double[a.getWidth() + a.getHeight() + 1][a.getHeight() + 1];
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[0].length; j++) {
        table[i][j] = 0;
      }
    }
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[0].length; j++) {
        System.out.print(table[i][j] + "  ");
      }
      System.out.println();
    }
    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < table[0].length - 1; j++) {
        table[i][j] = b.get(i, 0).value();
      }
    }
    for (int i = 1; i < a.getWidth() + 1; i++) {
      for (int j = 0; j < a.getHeight(); j++) {
        table[i][j] = a.get(i - 1, j).value();
      }
    }
    for (int i = 1; i < a.getWidth() + 1; i++) {
      for (int j = a.getHeight(); j < a.getHeight() + 1; j++) {
        table[i][j] = c.get(i - 1, 0).value();
      }
    }
    for (int i = a.getWidth() + 1; i < a.getWidth() + 1 + a.getHeight(); i++) {
      for (int j = 0; j < a.getHeight(); j++) {
        table[i][j] = b.get(i -a.getWidth() + 1, j).value();
      }
    }

    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[0].length; j++) {
        System.out.print(table[i][j] + "  ");
      }
      System.out.println();
    }

    return null;
  }

  private double calcFunInPoint(Point x, Expression function) {
    Map<Integer, NumericExpression> vars = new HashMap<>();
    for (int i = 0; i < x.getCoords().length; i++) {
      vars.put((i + 1), new NumericExpression(x.getCoords()[i]));
    }
    Expression newE = function.replaceVariableBy(vars);
    return newE.value();
  }

  @Override
  protected void showResult(Report<BigDecimal> report) {
    reportPrinter.print(report);
  }

}
