package org.beltser.mathlab.expressions.types;

import org.beltser.mathlab.expressions.BinaryExpression;
import org.beltser.mathlab.expressions.Expression;

import java.util.Map;

public class DivideExpression extends BinaryExpression {

    public DivideExpression(Expression leftSubExpression, Expression rightSubExpression) {
        super(leftSubExpression, rightSubExpression);
    }

    @Override
    public double value(double... vars) {
        return leftSubExpression.value(vars) / rightSubExpression.value(vars);
    }

    @Override
    public Expression replaceVariableBy(Map<Integer, NumericExpression> vars) {
        return new DivideExpression(
                leftSubExpression.replaceVariableBy(vars),
                rightSubExpression.replaceVariableBy(vars));
    }

    @Override
    public LexemType getLexemType() {
        return LexemType.DIVIDE;
    }

}
