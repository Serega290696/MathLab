package org.beltser.mathlab.expressions;

import org.beltser.mathlab.expressions.types.NumericExpression;
import org.beltser.mathlab.expressions.types.VariableExpression;
import org.beltser.mathlab.utils.ExpressionUtil;

import java.util.Set;

public abstract class UnaryExpression implements Expression {

    protected Expression subExpression;

    public UnaryExpression(Expression subExpression) {
        this.subExpression = subExpression;
    }

    public Set<Integer> variables() {
        return ExpressionUtil.extractVarsFromExpression(subExpression);
    }

    @Override
    public String toString() {
        String result;
        if ((!(subExpression instanceof NumericExpression)
                && !(subExpression instanceof VariableExpression))) {
            result = getOperationSymbol() + "(" + subExpression + ")";
        } else {
            result = getOperationSymbol() + subExpression;
        }
        return result;
    }

}
