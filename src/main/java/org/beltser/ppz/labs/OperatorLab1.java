package org.beltser.ppz.labs;

import org.beltser.Main;
import org.beltser.mathlab.Operator;
import org.beltser.mathlab.equations.Equation;
import org.beltser.mathlab.equations.impl.EquationImpl;
import org.beltser.mathlab.exception.TimeLimitException;
import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.expressions.types.VariableExpression;
import org.beltser.mathlab.matrix.Matrix;
import org.beltser.mathlab.report.Report;
import org.beltser.mathlab.report.ReportPrinter;
import org.beltser.mathlab.report.ReportPrinterConsole;
import org.beltser.mathlab.systems.MathSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OperatorLab1 extends Operator<Matrix> {

    private static final long DEFAULT_TIME_LIMIT = 10;
    private static final TimeUnit DEFAULT_TIME_LIMIT_TIMEUNIT = TimeUnit.SECONDS;

    private long timeLimit = DEFAULT_TIME_LIMIT;
    private TimeUnit timeLimitTimeunit = DEFAULT_TIME_LIMIT_TIMEUNIT;

    private static final String MATRIX_FIELD_NAME = "matrix";
    private static final String B_FIELD_NAME = "b";

    private ReportPrinter<Matrix> reportPrinter = new ReportPrinterConsole<>();

    public OperatorLab1(ReportPrinter reportPrinter) {
        this.reportPrinter = reportPrinter;
    }

    public OperatorLab1(long timeLimit, TimeUnit timeLimitTimeunit) {
        this.timeLimit = timeLimit;
        this.timeLimitTimeunit = timeLimitTimeunit;
    }

    @Override
    protected Map getInput() {
        System.out.print("\nPlease, enter matrix height: ");
        final int matrixHeight = read(3);
        System.out.print("\nPlease, enter matrix width: ");
        final int matrixWidth = read(3);
        System.out.println("\nEnter matrix elements.");
        final double[][] matrix = new double[matrixHeight][matrixWidth];
        if (Main.DEBUG_INPUT_MODE) {
            for (int i = 0; i < matrixHeight; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    System.out.print(" [");
                    matrix[i][j] = read((i + 1) + j + 1);
                    System.out.print("]");
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < matrixHeight; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    System.out.printf(" [%d:%d] = ", i + 1, j + 1);
                    matrix[i][j] = read((i + 1) + j + 1);
                }
            }
        }
        matrix[0][0] = 4;
        matrix[0][1] = 1;
        matrix[0][2] = 4;

        matrix[1][0] = 1;
        matrix[1][1] = 1;
        matrix[1][2] = 2;

        matrix[2][0] = 2;
        matrix[2][1] = -1;
        matrix[2][2] = 2;
        Map<String, Object> inputData = new HashMap<>();
        inputData.put(MATRIX_FIELD_NAME, matrix);
        double[][] b = new double[matrixHeight][1];
        if (Main.DEBUG_INPUT_MODE) {
            for (int i = 0; i < matrixHeight; i++) {
                System.out.print(" [");
                b[i][0] = read(100 + i + 1);
                System.out.print("]");
                System.out.println();
            }
        } else {
            for (int i = 0; i < matrixHeight; i++) {
                System.out.printf(" [%d:%d] = ", i + 1, 0);
                b[i][0] = read(100 + i + 1);
            }
        }
        b[0][0] = -3;
        b[1][0] = -4;
        b[2][0] = 3;
        inputData.put(B_FIELD_NAME, b);
        return inputData;
    }

    @Override
    protected Matrix compute(Map inputtedData) throws TimeLimitException {
        double[][] arrayMatrix = (double[][]) inputtedData.get(MATRIX_FIELD_NAME);
        double[][] arrayB = (double[][]) inputtedData.get(B_FIELD_NAME);
        Matrix b = new Matrix(arrayB);
        Matrix matrixA = new Matrix(arrayMatrix);
        System.out.println("\nMatrix:");
        System.out.println(matrixA);
        int width = arrayMatrix[0].length;
        int height = arrayMatrix.length;
        Matrix L = Matrix.newUpperTriangular(width, height);
//        Matrix Lt = Matrix.newLowerTriangular(height, width, 2);
        Matrix Lt = L.transpose();
        Matrix multiply = Lt.multiply(L);
        HashMap<Integer, NumericExpression> vars = new HashMap<>();
        Set<Equation> equations = new HashSet<>();
        Matrix resultMatrix = new Matrix(Lt.getWidth(), Lt.getHeight());
        for (int i = 0; i < multiply.getHeight(); i++) {
            for (int j = 0; j < multiply.getWidth(); j++) {
                equations.add(new EquationImpl(multiply.get(i, j), matrixA.get(i, j)));
                resultMatrix.set(i, j, multiply.get(i, j));
            }
        }
        MathSystem mathSystem = new MathSystem(
                equations
        );
        System.out.println(mathSystem);
        System.out.println("======== 1 ========");
        Map<Integer, NumericExpression> solution = mathSystem.solve();
        System.out.println(solution);
        System.out.println("======== 2 ========");
        System.out.println(L);
        System.out.println("======== 3 ========");
        L = L.setValue(solution);
        System.out.println(L);
        System.out.println("======== Check ========");
        multiply = L.transpose().multiply(L);
        System.out.println(multiply);
        System.out.println("======== 4 ========");
        Matrix y = new Matrix(1, height);
        for (int i = 0; i < y.getHeight(); i++) {
            y.set(i, 0, new VariableExpression(100 + i));
        }
        equations.clear();
        System.out.println("y = " + y);
        System.out.println("L = " + L);
        multiply = L.transpose().multiply(y);
        assert multiply.getWidth() == 1;
        for (int i = 0; i < multiply.getHeight(); i++) {
            equations.add(new EquationImpl(multiply.get(i, 0), b.get(i, 0)));
            resultMatrix.set(i, 0, multiply.get(i, 0));
        }
        System.out.println("multiply = " + multiply);
        mathSystem = new MathSystem(
                equations
        );
        System.out.println("======== 5 ========");
        solution = mathSystem.solve();
        System.out.println(solution);
        y = y.setValue(solution);
        System.out.println(y);
        System.out.println("======== Check ========");
        multiply = L.transpose().multiply(y);
        System.out.println(multiply);
        System.out.println("======== 6 ========");
        Matrix x = new Matrix(1, height);
        for (int i = 0; i < x.getHeight(); i++) {
            x.set(i, 0, new VariableExpression(100 + i));
        }
        equations.clear();
        multiply = L.multiply(x);
        assert multiply.getWidth() == 1;
        for (int i = 0; i < multiply.getHeight(); i++) {
            equations.add(new EquationImpl(multiply.get(i, 0), y.get(i, 0)));
            resultMatrix.set(i, 0, multiply.get(i, 0));
        }
        System.out.println("multiply = " + multiply);
        mathSystem = new MathSystem(
                equations
        );
        System.out.println("======== 7 ========");
        solution = mathSystem.solve();
        System.out.println(solution);
        x = x.setValue(solution);
        System.out.println(y);
        System.out.println("======== Check ========");
        multiply = L.multiply(x);
        System.out.println(multiply);
        System.out.println("======== Answer ========");
        System.out.println(x);

        System.out.println("=================================");
        System.out.println("======== Answer checking ========");
        System.out.println("=================================");
        System.out.println(matrixA);
        System.out.println("MULTIPLE");
        System.out.println(x);
        System.out.println("EQUALS");
        System.out.println(matrixA.multiply(x));
        System.out.println("IS EQUAL?");
        System.out.println(b);
        multiply = matrixA.multiply(x);
        System.out.println(multiply);

        return x;
    }

    @Override
    protected void showResult(Report<Matrix> report) {
        reportPrinter.print(report);
    }

}
