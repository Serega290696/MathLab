package org.beltser.mathlab.expressions.types;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.UnaryExpression;

import java.util.Map;

public class UnarMinusExpression extends UnaryExpression {

    public UnarMinusExpression(Expression subExpression) {
        super(subExpression);
    }

    @Override
    public double value(double... vars) {
        return -super.subExpression.value(vars);
    }

    @Override
    public Expression replaceVariableBy(Map<Integer, NumericExpression> vars) {
        return new UnarMinusExpression(
                subExpression.replaceVariableBy(vars));
    }

    @Override
    public LexemType getLexemType() {
        return LexemType.MINUS;
    }
}
