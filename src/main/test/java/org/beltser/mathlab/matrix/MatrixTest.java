package org.beltser.mathlab.matrix;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.types.ExpExpression;
import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.expressions.types.PlusExpression;
import org.beltser.mathlab.expressions.types.VariableExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MatrixTest {
    private Expression testExpression1;
    private Expression testExpression2;

    @Before
    public void setUp() {
        testExpression1 =
                new PlusExpression(
                        new ExpExpression(new NumericExpression(2)),
                        new NumericExpression(-8.5)
                );
        testExpression2 =
                new PlusExpression(
                        new VariableExpression(1),
                        new NumericExpression(-2008)
                );
    }

    @Test
    public void testConstructor() throws Exception {
        Matrix matrix = new Matrix(2, 2);
        Assert.assertEquals(matrix.get(0, 0), null);
        Assert.assertEquals(matrix.get(0, 1), null);
        Assert.assertEquals(matrix.get(1, 0), null);
        Assert.assertEquals(matrix.get(1, 1), null);
    }

    @Test
    public void testConstructorDouble() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4}, {0.7, 0.8}});
        Assert.assertEquals(matrix.get(0, 0).value(), 0.3, 0);
        Assert.assertEquals(matrix.get(0, 1).value(), 0.4, 0);
        Assert.assertEquals(matrix.get(1, 0).value(), 0.7, 0);
        Assert.assertEquals(matrix.get(1, 1).value(), 0.8, 0);
    }

    @Test
    public void testConstructorDoubleBoxing() throws Exception {
        Matrix matrix = new Matrix(new Double[][]{{0.3, 0.4}, {0.7, 0.8}});
        Assert.assertEquals(matrix.get(0, 0).value(), 0.3, 0);
        Assert.assertEquals(matrix.get(0, 1).value(), 0.4, 0);
        Assert.assertEquals(matrix.get(1, 0).value(), 0.7, 0);
        Assert.assertEquals(matrix.get(1, 1).value(), 0.8, 0);
    }

    @Test
    public void testSet() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4}, {0.7, 0.8}});
        matrix.set(1, 0, testExpression1);
        matrix.set(1, 1, testExpression2);
        Assert.assertEquals(matrix.get(1, 0), testExpression1);
        Assert.assertEquals(matrix.get(1, 1), testExpression2);
    }

    @Test
    public void testGet() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4}, {0.7, 0.8}});
        Assert.assertEquals(matrix.get(0, 0).value(), 0.3, 0);
        Assert.assertEquals(matrix.get(0, 1).value(), 0.4, 0);
        Assert.assertEquals(matrix.get(1, 0).value(), 0.7, 0);
        Assert.assertEquals(matrix.get(1, 1).value(), 0.8, 0);
    }

    @Test
    public void testSetValue() throws Exception {
        Matrix matrix = MatrixFactory.newCommonMatrix(3, 3, 0);
        Map<Integer, NumericExpression> vars = new HashMap<>();
        vars.put(12, new NumericExpression(14));
        vars.put(13, new NumericExpression(15));
        vars.put(23, new NumericExpression(16));
        vars.put(33, new NumericExpression(17));
        Matrix matrix2 = matrix.setValue(vars);
        Assert.assertEquals(matrix2.get(0, 1).value(), 14, 0);
        Assert.assertEquals(matrix2.get(0, 2).value(), 15, 0);
        Assert.assertEquals(matrix2.get(1, 2).value(), 16, 0);
        Assert.assertEquals(matrix2.get(2, 2).value(), 17, 0);
    }

    @Test
    public void testTranspose() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4}, {0.7, 0.8}});
        Matrix transposed = matrix.transpose();
        Assert.assertEquals(transposed.get(0, 0).value(), 0.3, 0);
        Assert.assertEquals(transposed.get(0, 1).value(), 0.7, 0);
        Assert.assertEquals(transposed.get(1, 0).value(), 0.4, 0);
        Assert.assertEquals(transposed.get(1, 1).value(), 0.8, 0);
    }

    @Test
    public void testMultiply() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4}, {0.7, 0.8}});
        Matrix matrix2 = new Matrix(new double[][]{{-20, 0.1}, {0.2, 0.3}});
        Matrix multiplication = matrix.multiply(matrix2);
        Assert.assertEquals(multiplication.get(0, 0).value(), -5.92, 0);
        Assert.assertEquals(multiplication.get(0, 1).value(), 0.15, 0);
        Assert.assertEquals(multiplication.get(1, 0).value(), -13.84, 0);
        Assert.assertEquals(multiplication.get(1, 1).value(), 0.31, 0);
    }

    @Test
    public void testGetWidth() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4, 11}, {0.7, 0.8, 12}});
        Assert.assertEquals(matrix.getWidth(), 3, 0);
    }

    @Test
    public void testGetHeight() throws Exception {
        Matrix matrix = new Matrix(new double[][]{{0.3, 0.4, 11}, {0.7, 0.8, 12}});
        Assert.assertEquals(matrix.getHeight(), 2, 0);
    }

    @Test
    public void testIsInitialized() throws Exception {
        Matrix matrix = new Matrix(7, 4);
        Matrix initializedMatrix = new Matrix(new double[][]{{0.3, 0.4, 11}, {0.7, 0.8, 12}});
        Assert.assertFalse(matrix.isInitialized());
        Assert.assertTrue(initializedMatrix.isInitialized());
    }

    @Test
    public void testDet() throws Exception {
//        Matrix matrix = new Matrix(new double[][]{{3, 3,3}, {3, 3,3}, {3, 3,2}});
//        double det = matrix.det();
//        Assert.assertEquals(0, det, 0.01);
        Matrix matrix = new Matrix(new double[][]{{7, 8, 9, 9}, {-4, -5, -6, -7}, {0.2, 0.3, 0.4, 0.5}, {250, -250, 400, -40}});
        double det = matrix.det();
        Assert.assertEquals(-230, det, 0.01);
    }
    @Test
    public void isMatrixPositive() throws Exception {
        Matrix positiveMatrix = new Matrix(new double[][]{{3, 1},{2, 3}});
        boolean positive = positiveMatrix.isPositive();
        Assert.assertTrue(positive);
        Matrix negateMatrix = new Matrix(new double[][]{{3, 10},{12, 3}});
        positive = negateMatrix.isPositive();
        Assert.assertFalse(positive);
    }

}