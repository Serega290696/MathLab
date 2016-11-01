package org.beltser.mathlab.expressions.types;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.UnaryExpression;

import java.util.Map;

public class CosExpression extends UnaryExpression {

    public CosExpression(Expression subExpression) {
        super(subExpression);
    }

    @Override
    public double value(double... vars) {
        return Math.cos(super.subExpression.value(vars));
    }
    @Override
    public Expression replaceVariableBy(Map<Integer, NumericExpression> vars) {
        return new CosExpression(super.subExpression.replaceVariableBy(vars));
    }

    @Override
    public LexemType getLexemType() {
        return LexemType.COS;
    }
}
