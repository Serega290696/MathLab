package org.beltser.mathlab.matrix;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.types.MultipleExpression;
import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.expressions.types.PlusExpression;

import java.util.Map;

public class Matrix {

    private int width;
    private int height;
    private Expression[][] matrixArray;

    public Matrix(int width, int height) {
        matrixArray = new Expression[height][width];
        this.width = width;
        this.height = height;
    }

    public Matrix(Expression[][] twoDimensionalArray) {
        this.height = twoDimensionalArray.length;
        this.width = twoDimensionalArray[0].length;
        matrixArray = new Expression[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                set(i, j, twoDimensionalArray[i][j]);
            }
        }
    }

    public <T extends Number> Matrix(double[][] twoDimensionalArray) {
        this.height = twoDimensionalArray.length;
        this.width = twoDimensionalArray[0].length;
        matrixArray = new Expression[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                set(i, j, new NumericExpression(twoDimensionalArray[i][j]));
            }
        }
    }

    public Matrix(Double[][] twoDimensionalArray) throws Exception {
        this.height = twoDimensionalArray.length;
        this.width = twoDimensionalArray[0].length;
        matrixArray = new Expression[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                set(i, j, new NumericExpression(twoDimensionalArray[i][j].doubleValue()));
            }
        }
    }

    public void set(int i, int j, Expression expression) {
        matrixArray[i][j] = expression;
    }

    public Expression get(int i, int j) {
        return matrixArray[i][j];
    }

    public Matrix setValue(Map<Integer, NumericExpression> vars) {
//        //System.out.println("Matrix.setValue");
        Matrix matrix = new Matrix(this.matrixArray);
//        //System.out.println("this = " + matrix);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Expression expression = matrix.get(i, j).replaceVariableBy(vars);
                if (expression.variables().size() == 0) {
                    matrix.set(i, j, new NumericExpression(expression.value()));
                } else {
                    matrix.set(i, j, expression);
                }
            }
        }
        return matrix;
    }

    public Matrix transpose() {
        //System.out.println("Matrix.transpose");
        //System.out.println("Original:");
        //System.out.println(this);
        Matrix transposed = new Matrix(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                transposed.set(j, i, get(i, j));
            }
        }
        //System.out.println("Transposed:");
        //System.out.println(transposed);
        return transposed;
    }

    public Matrix multiply(Matrix multiplicand) {
        Matrix multiplication = new Matrix(multiplicand.getWidth(), height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < multiplicand.getWidth(); j++) {
                Expression[] sum = new Expression[width];
                for (int k = 0; k < width; k++) {
                    sum[k] = new MultipleExpression(get(i, k), multiplicand.get(k, j));
                }
                PlusExpression plusExpression = new PlusExpression(sum);
                if (plusExpression.variables().size() == 0) {
                    NumericExpression numericExpression = new NumericExpression(plusExpression.value());
                    multiplication.set(i, j,
                            numericExpression
                    );
                } else {
                    multiplication.set(i, j,
                            plusExpression
                    );
                }
            }
        }
        return multiplication;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                string.append("[").append(get(i, j)).append("] ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    public boolean isInitialized() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (get(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSymmetric() {
        if (width != height) {
            return false;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (get(j, i).value() != get(i, j).value()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPositive() {
        Matrix matrix = this;
        for (int i = 0; i < height; i++) {
            double det = matrix.det();
            if(det <= 0) {
                return false;
            }
            matrix = matrix.crossOutCol(width - i).crossOutRow(height - i);
        }
        return true;
    }

    public double det() {
        if (width != height) {
            throw new RuntimeException("Not supported operation");
        }
        if (width > 2) {
            double det = 0;
            for (int i = 0; i < width; i++) {
                det += (Math.signum(i % 2 - 0.5)) * get(0, i).value() * this.crossOutRow(1).crossOutCol(i + 1).det();
            }
            return det;
        }
        if (width == 2) {// height the same (height = 2)
            return get(0, 0).value() * get(1, 1).value() - get(0, 1).value() * get(1, 0).value();
        }
        if (width == 1) {// height the same (height = 1)
            return get(0, 0).value();
        }
        throw new RuntimeException("Not supported operation");
    }

    public boolean subMatrix(int x1, int y1, int x2, int y2) {
        Expression[][] subMatrix = new Expression[y2 - y1][x2 - x1];
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                subMatrix[i][j] = get(i, j);
            }
        }
        return false;
    }

    public Matrix crossOutRow(int row) {
        row--;
        Matrix newMatrix = new Matrix(width, height - 1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < row) {
                    newMatrix.set(i, j, get(i, j));
                } else if (i > row) {
                    newMatrix.set(i - 1, j, get(i, j));
                }
            }
        }
        return newMatrix;
    }

    public Matrix crossOutCol(int column) {
        column--;
        Matrix newMatrix = new Matrix(width - 1, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j < column) {
                    newMatrix.set(i, j, get(i, j));
                } else if (j > column) {
                    newMatrix.set(i, j - 1, get(i, j));
                }
            }
        }
        return newMatrix;
    }
}