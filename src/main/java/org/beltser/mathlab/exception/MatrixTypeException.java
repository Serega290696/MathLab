package org.beltser.mathlab.exception;

public class MatrixTypeException extends IllegalArgumentException {
  public MatrixTypeException() {
    this("Wrong matrix type");
  }

  public MatrixTypeException(String message) {
    super(message);
  }
}
