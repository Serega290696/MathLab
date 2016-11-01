package org.beltser.mathlab.equations;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.types.NumericExpression;

import java.util.Map;
import java.util.Set;

public interface Equation {

    Map<Integer, NumericExpression> solve();

    Equation simplify();

    Set<Integer> variables();


    void setLeftExpression(Expression leftExpression);

    void setRightExpression(Expression rightExpression);

    Expression getLeftExpression();

    Expression getRightExpression();

    Equation replaceVar(Map<Integer, NumericExpression> vars);

}
