package org.beltser.mathlab.exception;

public class ExpressionParsingException extends Exception {
  public ExpressionParsingException() {
    this("Expression parsing exception");
  }

  public ExpressionParsingException(String corruptedExpression) {
    super("Expression parsing exception: " + corruptedExpression);
  }
}
