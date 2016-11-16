package org.beltser.mathlab.matrix;

import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.expressions.types.VariableExpression;

public class MatrixFactory {

  public static Matrix newUpperTriangular(int width, int height) {
    return newUpperTriangular(width, height, 0).transpose();
  }

  public static Matrix newUpperTriangular(int width, int height, int varsPortion) {
    return newLowerTriangular(width, height, varsPortion).transpose();
  }

  public static Matrix newLowerTriangular(int width, int height) {
    return newLowerTriangular(width, height, 0);
  }

  public static Matrix newLowerTriangular(int width, int height, int varsIndexingOffset) {
    Matrix matrix = new Matrix(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (i < j) {
          matrix.set(i, j, new NumericExpression(0d));
        } else {
          matrix.set(i, j, new VariableExpression(100 * varsIndexingOffset + (i + 1) * 10 + j + 1));
        }
      }
    }
    return matrix;
  }

  public static Matrix newSymmetricMatrix(int width, int height, int varsIndexingOffset) {
    Matrix matrix = new Matrix(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (i < j) {
          matrix.set(i, j, new VariableExpression(100 * varsIndexingOffset + (i + 1) * 10 + j + 1));
        } else {
          matrix.set(i, j, new VariableExpression(100 * varsIndexingOffset + (j + 1) * 10 + i + 1));
        }
      }
    }
    return matrix;
  }

  public static Matrix newCommonMatrix(int width, int height, int varsIndexingOffset) {
    Matrix matrix = new Matrix(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
          matrix.set(i, j, new VariableExpression(100 * varsIndexingOffset + (i + 1) * 10 + j + 1));
      }
    }
    return matrix;
  }

}
