package org.beltser.ppz.labs;

import org.beltser.mathlab.Operator;
import org.beltser.mathlab.exception.TimeLimitException;
import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.parser.ExpressionParser;
import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.linear_geometry.Point;
import org.beltser.mathlab.report.Report;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterConsole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OperatorLab4 extends Operator<Point> {

  private static final long DEFAULT_TIME_LIMIT = 5;
  private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

  private long timeLimit = DEFAULT_TIME_LIMIT;
  private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

  public static final String FUNCTION_FIELD_NAME = "function";
  public static final String A_FIELD_NAME = "a";
  public static final String B_FIELD_NAME = "b";
  public static final String C_FIELD_NAME = "c";

  private ReportPrinter<Point> reportPrinter = new ReportPrinterConsole<>();

  public OperatorLab4(ReportPrinter reportPrinter) {
    this.reportPrinter = reportPrinter;
  }

  public OperatorLab4(long timeLimit, TimeUnit timeLimitTimeunit) {
    this.timeLimit = timeLimit;
    this.timeLimitTimeunit = timeLimitTimeunit;
  }

  protected Map getInput() {
//        System.out.println("Please, enter expression: ");
//        final String stringExpression = read("0.5*x*y^2-y");//x+y
//        System.out.println("Enter x0: ");
//        final double x0 = read(0);
//        System.out.println("Enter y0: ");
//        final double y0 = read(2);
//        System.out.println("Enter a: ");
//        final double a = read(0);
//        System.out.println("Enter b: ");
//        final double b = read(2);
//
//        Map<String, Object> inputData = new HashMap<>();
//        inputData.put(EXPRESSION_FIELD_NAME, stringExpression);
//        inputData.put(X_ZERO_FIELD_NAME, x0);
//        inputData.put(Y_ZERO_FIELD_NAME, y0);
//        inputData.put(A_FIELD_NAME, a);
//        inputData.put(B_FIELD_NAME, b);
//        return inputData;
    return null;
  }


  protected Point compute(Map inputtedData) throws TimeLimitException {
    final boolean DEBUG = false;
    //read input
    long beginTime = System.currentTimeMillis();
    String functionString;
    double a;
    double b;
    double c;
    if (DEBUG) {
//      functionString = "x1+x2";
      functionString = "100*((x2-x1^2)^2)+(1-x1)^2";
      a = 1;
      b = 0.5;
      c = 2;
    } else {
      functionString = (String) inputtedData.get(FUNCTION_FIELD_NAME);
      a = (double) inputtedData.get(A_FIELD_NAME);
      b = (double) inputtedData.get(B_FIELD_NAME);
      c = (double) inputtedData.get(C_FIELD_NAME);
    }
    Expression function = ExpressionParser.parse(functionString);
    System.out.println("function = " + function);
    //start algorithm
    System.out.println("Start calculations. . .");
    //init vars
    Set<Integer> variables = function.variables();
    int n = variables.size();
    System.out.println("n = " + n);

    //yet not inited vars
    List<Double> funValues = new ArrayList<>(n + 1);
    List<Point> points = new ArrayList<>(n + 1);
    Map<Integer, NumericExpression> vars;

    if (DEBUG) {
      points.add(new Point(10, 9));
      points.add(new Point(10, -2));
      points.add(new Point(21, 1));
      for (int i = 0; i < points.size(); i++) {
        funValues.add(calcFunInPoint(points.get(i), function));
      }
    } else {
      points.add(new Point(10, 9));
      points.add(new Point(10, -2));
      points.add(new Point(21, 1));
      //init base figure
//      final double minPointCoord = 0.1;
//      final double maxPointCoord = 0.9;
//      vars = new HashMap<>();
//      Iterator<Integer> iterator = variables.iterator();
      for (int i = 0; i < n + 1; i++) {
//        double[] coords = new double[i + 1];
//        for (int j = 0; j < i; j++) {
//          if (j < i + 1) {
//            coords[j] = minPointCoord + maxPointCoord * Math.random();
//          } else {
//            coords[j] = 0;
//          }
//          vars.put(iterator.next(), new NumericExpression(coords[j]));
//        }
//        Point p = new Point(coords);
//        points.add(p);
        funValues.add(calcFunInPoint(points.get(i), function));
      }
    }


    //begin loop
    //begin loop
    //begin loop
    //begin loop
    //begin loop
    double fh = 0;
    double fg = 0;
    double fl = 0;

    Point xh = null;
    Point xg = null;
    Point xl = null;
    for (int counter = 0; counter < 70; counter++) {
      // =========================== STEP #8 ===========================
      // ===============================================================
      System.out.println("\t\t\tSTEP-8");
      if (counter > 0) {
        funValues.remove(0);
        funValues.add(0, calcFunInPoint(xh, function));
        points.remove(0);
        points.add(0, xh);
        System.out.println("\t\tfh = " + fh);
        System.out.println("\t\tfl = " + fl);
      }
      System.out.println(counter + ". --------------- " + points.size() + " x " + funValues.size());
      for (int i = 0; i < points.size(); i++) {
        System.out.println("\t\tp#" + i + ": " + points.get(i) + " ===> " + funValues.get(i));
      }
      // =========================== STEP #1 ===========================
      // ===============================================================
      System.out.println("\t\t\tSTEP-1");
      //sort x
      for (int i = 0; i < funValues.size() - 1; i++) {
        for (int j = 0; j < funValues.size() - 1; j++) {
          Double f1 = funValues.get(j);
          Double f2 = funValues.get(j + 1);
          if (f1 < f2) {
            funValues.remove(j);
            funValues.remove(j);
            Point removedPJ = points.remove(j);
            Point removedPJ1 = points.remove(j);

            funValues.add(j, f2);
            funValues.add(j + 1, f1);
            points.add(j, removedPJ1);
            points.add(j + 1, removedPJ);
          }
        }
      }
      // find xh, xg, xl
      fh = funValues.get(0);
      fg = funValues.get(1);
      fl = funValues.get(funValues.size() - 1);
      xh = points.get(0);
      xg = points.get(1);
      xl = points.get(points.size() - 1);
//      for (int i = 0; i < funValues.size(); i++) {
//        System.out.println("F: " + funValues.get(i));
//      }
//      for (int i = 0; i < points.size(); i++) {
//        System.out.println("P: " + points.get(i));
//      }
      // =========================== STEP #2 ===========================
      // ===============================================================
      Point tmpSumPoint = points.get(1);
      for (int i = 2; i < points.size(); i++) {
        tmpSumPoint = tmpSumPoint.plus(points.get(i));
      }
      System.out.println("\t\t\tSTEP-2");
      Point xc = tmpSumPoint.divide(n);
//      System.out.println("xc = " + xc);
      // =========================== STEP #3 ===========================
      // ===============================================================
      System.out.println("\t\t\tSTEP-3");
      Point xr = xc.multiple(1 + a).minus(xh.multiple(a));
      double fr = calcFunInPoint(xr, function);
      System.out.println("\t\t\t\txr = " + xr + " ===> " + fr);
      // =========================== STEP #4 ===========================
      // ===============================================================
      System.out.println("\t\t\tSTEP-4");

      boolean endIter = false;
      // STEP #4
      if (fr <= fl) {
        // STEP #4-a
        System.out.println("\t\t\t\tSTEP-4 a");
        Point xe;
        xe = xc.multiple(1d - c).plus(xr.multiple(c));
        double fe = calcFunInPoint(xe, function);
        System.out.println("fe = " + fe);
        System.out.println("xe = " + xe);
        if (fe < fl) {
          xh = xe;
          fh = fe;
          endIter = true;
        }
        if (fe > fl) {
          xh = xr;
          fh = fr;
          endIter = true;
        }
      } else if (fl < fr && fr < fg) {
        // STEP #4-b
        System.out.println("\t\t\t\tSTEP-4 b");
        xh = xr;
        fh = fr; //todo ???
        endIter = true;
      } else if (fh > fr && fr > fg) {
        // STEP #4-c
        System.out.println("\t\t\t\tSTEP-4 c");
        Point tmpPoint;
        tmpPoint = xr;
        xr = xh;
        xh = tmpPoint;
        double tmp;
        tmp = fr;
        fr = fh;
        fh = tmp;
      } else if (fr > fg) {
        // STEP #4-d
        System.out.println("\t\t\t\tSTEP-4 d");

      }

      if (endIter) {
        System.out.println("xh = " + xh);
        continue;
      }

      // =========================== STEP #5 ===========================
      // ===============================================================
      System.out.println("\t\t\tSTEP-5");
      Point xs = xh.multiple(b).plus(xc.multiple(1d - b));
      double fs = calcFunInPoint(xs, function);
      System.out.println("\t\t\t\txs = " + xs + " ===> " + fs);
      // =========================== STEP #6 ===========================
      // ===============================================================
      if (fs < fh) {
        System.out.println("\t\t\tSTEP-6");
        xh = xs;
        endIter = true;//todo!!!
      }
      if (endIter) {
        continue;
      }
      // =========================== STEP #7 ===========================
      // ===============================================================
      if (fs > fh) {
        List<Point> newPoints = new ArrayList<>();
        System.out.println("\t\t\tSTEP-7");
        for (int i = 0; i < points.size(); i++) {
          Point xi = points.get(i);
          if (xi != xl) {
            newPoints.add(xl.plus(xi.minus(xl).divide(2)));
          }
        }
        newPoints.add(xl);
        points = newPoints;
      }
    }

    return points.get(0);
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
  protected void showResult(Report<Point> report) {
    reportPrinter.print(report);
  }

}
