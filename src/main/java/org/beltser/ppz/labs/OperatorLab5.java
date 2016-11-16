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
import org.beltser.mathlab.expressions.types.VariableExpression;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class OperatorLab5 extends Operator<Double> {

    private static final long DEFAULT_TIME_LIMIT = 5;
    private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

    private long timeLimit = DEFAULT_TIME_LIMIT;
    private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

    public static final String FUNCTION_FIELD_NAME = "function";
    public static final String A_FIELD_NAME = "a";
    public static final String B_FIELD_NAME = "b";
    public static final String C_FIELD_NAME = "c";

    private ReportPrinter<Double> reportPrinter = new ReportPrinterConsole<>();

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


    protected Double compute(Map inputtedData) throws TimeLimitException {
        final boolean DEBUG = false;
        //read input
        long beginTime = System.currentTimeMillis();
        Matrix a;
        Matrix b;
        Matrix c;
        if (DEBUG) {
            a = new Matrix(new double[][]{{6, 3, 1, 4}, {2, 4, 5, 1}, {1, 2, 4, 3}});
            b = new Matrix(new double[][]{{252}, {144}, {80}});
            c = new Matrix(new double[][]{{48}, {33}, {16}, {22}});
        } else {
            a = new Matrix((double[][]) inputtedData.get(A_FIELD_NAME));
            b = new Matrix((double[][]) inputtedData.get(B_FIELD_NAME));
            c = new Matrix((double[][]) inputtedData.get(C_FIELD_NAME));
        }

        //start algorithm
        System.out.println("Start calculations. . .");
        //init vars
        double[][] table = new double[a.getHeight() + 1][a.getWidth() + a.getHeight() + 2];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = 0;
            }
        }
        for (int i = 0; i < table.length - 1; i++) {
            for (int j = 0; j < 1; j++) {
                table[i][j] = b.get(i, 0).value();
            }
        }
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 1; j < a.getWidth() + 1; j++) {
                table[i][j] = a.get(i, j - 1).value();
            }
        }
        for (int i = a.getHeight(); i < a.getHeight() + 1; i++) {
            for (int j = 1; j < c.getHeight() + 1; j++) {
                table[i][j] = -c.get(j - 1, 0).value();
            }
        }
        for (int i = 0; i < a.getHeight(); i++) {
            table[i][1 + a.getWidth() + i] = 1;
        }
        printTable(table);

        Expression[] basis = new Expression[a.getHeight()];
        for (int i = 0; i < a.getHeight(); i++) {
            basis[i] = new VariableExpression(a.getWidth() + i + 1);
        }
        System.out.println("Basis: " + Arrays.toString(basis));
        for (int counter = 0; counter < 100; counter++) {
            System.out.println(counter + ". ");

            // finish ?
            int indexCol = -1;
            for (int j = 1; j < 1 + a.getWidth(); j++) {
                if (table[a.getHeight()][j] < 0) {
                    if (indexCol != -1) {
                        if (table[a.getHeight()][j] < table[a.getHeight()][indexCol]) {
                            indexCol = j;
                        }
                    } else {
                        indexCol = j;
                    }
                }
            }
            if (indexCol == -1) {
                System.out.println("Plain is optimal");
                break;
            }
            System.out.println("indexCol = " + indexCol);
            for (int i = 0; i < a.getHeight(); i++) {
                for (int j = a.getWidth() + a.getHeight() + 1; j < a.getWidth() + 2 + a.getHeight(); j++) {
                    table[i][j] = table[i][0] / table[i][indexCol];//b.get(i, 0).value();
                }
            }
            int indexRow = 0;
            for (int i = 0; i < a.getHeight(); i++) {
                if (table[i][1 + a.getHeight() + a.getWidth()] < table[indexRow][1 + a.getHeight() + a.getWidth()]) {
                    indexRow = i;
                }
            }
            System.out.println("indexRow = " + indexRow);
            basis[indexRow] = new VariableExpression(indexRow + 1);
            double ge = table[indexRow][indexCol];
            printTable(table);
            double[][] newTable = new double[a.getHeight() + 1][a.getWidth() + a.getHeight() + 2];
            for (int i = 0; i < a.getHeight() + 1; i++) {
                for (int j = 0; j < a.getWidth() + a.getHeight() + 1; j++) {
                    if (i == indexRow && j == indexCol) {
                        newTable[i][j] = 1;
                    } else if (i == indexRow) {
                        newTable[i][j] = table[i][j] / ge;
                    } else if (j == indexCol) {
                        newTable[i][j] = 0;
                    } else {
                        newTable[i][j] = table[i][j] - table[i][indexCol] * table[indexRow][j] / ge;
                    }
                }
            }
            table = newTable;
            printTable(newTable);
            System.out.println("Basis: " + Arrays.toString(basis));
        }
        System.out.println("Basis: " + Arrays.toString(basis));
        double result = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; i++) {
            VariableExpression v;
            if (basis[i] instanceof VariableExpression) {
                v = (VariableExpression) basis[i];
                if (v.getValue() > a.getWidth()) {
                    break;
                } else {
                    builder.append("X").append(v.getValue()).append(" are ").append(table[i][0]).append(" pieces.\n");
                }
            } else {
                break;
            }
        }
        builder.append("Total income: " + table[a.getHeight()][0]);
        System.out.println();
        System.out.println("========================================");
        System.out.println("Answer: ");
        System.out.println(builder.toString());
        System.out.println();

        return table[a.getHeight()][0];
    }

    private void printTable(double[][] table) {
//        for (int i = 0; i < table.length; i++) {
//            for (int j = 0; j < table[0].length; j++) {
//                System.out.print(table[i][j] + "\t\t\t");
//            }
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println();
    }

    @Override
    protected void showResult(Report<Double> report) {
        reportPrinter.print(report);
    }

}
