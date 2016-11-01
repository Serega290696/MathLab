package org.beltser.mathlab.expressions.types;

import org.beltser.mathlab.expressions.Expression;
import org.beltser.mathlab.expressions.NullaryExpression;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumericExpression extends NullaryExpression {

    public Double value;

    public NumericExpression(double value) {
        super(value);
    }

    public Set<Integer> variables() {
        return new HashSet<>();
    }

    public double value(double... vars) {
        return value;
    }

    public Expression replaceVariableBy(Map<Integer, NumericExpression> vars) {
        return new NumericExpression(value);
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.doubleValue();
    }

    @Override
    public LexemType getLexemType() {
        return LexemType.NUMERIC;
    }

    @Override
    public Double getValue() {
        return value;
    }
}
