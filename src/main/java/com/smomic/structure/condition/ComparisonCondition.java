package com.smomic.structure.condition;

import com.smomic.exception.IncorrectConditionException;
import com.smomic.exception.IncorrectUnitTypeException;
import com.smomic.structure.*;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.element.Variable;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.List;

public class ComparisonCondition extends Condition {
    private PrimaryCondition firstCondition;
    private PrimaryCondition secondCondition;
    private OperatorType operator;

    public ComparisonCondition(PrimaryCondition firstCondition, PrimaryCondition secondCondition, OperatorType operator) {
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
        this.operator = operator;
    }

    @Override
    public NodeType getType() {
        return null;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        if (secondCondition == null) {
            Element element = firstCondition.execute(scope, functions, imports);

            if (element.getType().equals(NodeType.ConditionBool))
                return element;
            if (element.getType().equals(NodeType.Number)) {
                return (((Number) element).getValue().equals(0.0)) ? new ConditionBool(false)
                        : new ConditionBool(true);
            }
            if (element.getType().equals(NodeType.Literal)) {
                return (((Literal) element).getNumber().getValue().equals(0.0)) ? new ConditionBool(false)
                        : new ConditionBool(true);
            }
        }

        try {
            Element valueFirst = convert(firstCondition.execute(scope, functions, imports), imports);
            Element valueSecond = convert(secondCondition.execute(scope, functions, imports), imports);

            if (valueFirst == null && valueSecond == null)
                return null;

            checkType(valueFirst, valueSecond);
            return calculateValue(valueFirst, valueSecond);
        } catch (IncorrectUnitTypeException e) {
            e.show();
        } catch (IncorrectConditionException e) {
            e.show();
        }

        return null;
    }

    private Element convert(Element value, List<ImportDirective> imports) throws IncorrectUnitTypeException {
        if (value.getType().equals(NodeType.Number))
            return value;

        if (value.getType().equals(NodeType.Literal)) {
            Literal literal = (Literal) value;
            List<Pair<String, Double>> arg = Common.getUnitsPair(literal.getUnit(), imports);
            if (arg == null || arg.get(0) == null || arg.get(1) == null)
                throw new IncorrectUnitTypeException(((Literal) value).getUnit(), ((Literal) value).getPosition());

            Number number = new Number(literal.getNumber().getValue() * arg.get(1).getValue(),
                    literal.getPosition());
            literal.setNumber(number);
            literal.setUnit(arg.get(1).getKey());
            return literal;
        }

        return null;
    }

    private void checkType(Element argFirst, Element argSecond) throws IncorrectConditionException {
        if (argFirst != null && argSecond == null)
            checkTypeAndThrow(argFirst);

        else if (argFirst == null && argSecond != null)
            checkTypeAndThrow(argSecond);

        if (isZero(argFirst) && isZero(argSecond) && !argFirst.getType().equals(argSecond.getType()))
            checkTypeAndThrow(argSecond);
    }

    private void checkTypeAndThrow(Element arg) throws IncorrectConditionException {
        if (arg.getType().equals(NodeType.Literal))
            throw new IncorrectConditionException(((Literal) arg).getPosition());
        else if (arg.getType().equals(NodeType.Number))
            throw new IncorrectConditionException(((Number) arg).getPosition());
        else
            throw new IncorrectConditionException(((Variable) arg).getPosition());
    }

    private boolean isZero(Element element) {
        if (element.getType().equals(NodeType.Literal)) {
            if (((Literal) element).getNumber().getValue().equals(0.0))
                return false;
        }
        if (element.getType().equals(NodeType.Number)) {
            if (((Number) element).getValue().equals(0.0))
                return false;
        }
        return true;
    }

    private ConditionBool calculateValue(Element argFirst, Element argSecond) {
        Double valFirst = getDouble(argFirst);
        Double valSecond = getDouble(argSecond);
        boolean result;

        if (operator.equals(OperatorType.Equality)) {
            result = valFirst.equals(valSecond);

        } else if (operator.equals(OperatorType.Inequality)) {
            result = !valFirst.equals(valSecond);

        } else if (operator.equals(OperatorType.Greater)) {
            result = valFirst > valSecond;

        } else if (operator.equals(OperatorType.GreaterEqual)) {
            result = valFirst >= valSecond;

        } else if (operator.equals(OperatorType.Less)) {
            result = valFirst < valSecond;

        } else {
            result = valFirst <= valSecond;
        }

        return new ConditionBool(result);
    }

    private Double getDouble(Element arg) {
        if (arg.getType().equals(NodeType.Literal))
            return ((Literal) arg).getNumber().getValue();

        else
            return ((Number) arg).getValue();
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
