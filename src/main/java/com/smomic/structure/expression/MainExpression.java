package com.smomic.structure.expression;

import com.smomic.exception.IncorrectExpressionTypeException;
import com.smomic.exception.IncorrectUnitTypeException;
import com.smomic.structure.*;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.List;

public class MainExpression extends Expression {
    private List<MultiExpression> operands;
    private List<OperatorType> operatorList;

    public MainExpression(List<MultiExpression> operands, List<OperatorType> operatorList) {
        this.operands = operands;
        this.operatorList = operatorList;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        Element value = operands.get(0).execute(scope, functions, imports);
        Element result = null;
        if (value != null) {
            if (value.getType().equals(NodeType.Number))
                result = new Number(((Number) value).getValue(), ((Number) value).getPosition());
            else if (value.getType().equals(NodeType.Literal))
                result = new Literal(((Literal) value).getNumber().getValue(), ((Literal) value).getUnit(),
                        ((Literal) value).getPosition());
        }
        else return null;

        if (operatorList.size() > 0 && result != null) {
            for (int i = 1; i < operands.size(); ++i) {
                try {
                    result = calculateResult(result, operands.get(i).execute(scope, functions, imports),
                            operatorList.get(i - 1), imports);
                } catch (IncorrectExpressionTypeException e) {
                    e.show();
                } catch (IncorrectUnitTypeException e) {
                    e.show();
                }
            }
        }

        return result;
    }

    private Element calculateResult(Element argFirst, Element argSecond, OperatorType operator,
                                    List<ImportDirective> imports)
            throws IncorrectExpressionTypeException, IncorrectUnitTypeException {
        if (argFirst.getType().equals(argSecond.getType())) {
            if (argFirst.getType().equals(NodeType.Number)) {
                return calculateNumbers((Number) argFirst, (Number) argSecond, operator);
            } else {
                if (((Literal) argFirst).getUnit().equals(((Literal) argSecond).getUnit())) {
                    return calculateSameLiterals((Literal) argFirst, (Literal) argSecond, operator);
                } else
                    return calculateDiffLiterals((Literal) argFirst, (Literal) argSecond, operator, imports);
            }
        }

        if (argSecond.getType().equals(NodeType.Literal))
            throw new IncorrectExpressionTypeException(((Literal)argSecond).getPosition());
        else
            throw new IncorrectExpressionTypeException(((Number)argSecond).getPosition());
    }

    private Element calculateNumbers(Number argFirst, Number argSecond, OperatorType operator) {
        if (operator.equals(OperatorType.Plus))
            argFirst.setValue(argFirst.getValue() + argSecond.getValue());

        else
            argFirst.setValue(argFirst.getValue() - argSecond.getValue());

        return argFirst;
    }

    private Element calculateSameLiterals(Literal argFirst, Literal argSecond, OperatorType operator) {
        argFirst.setNumber((Number) calculateNumbers(argFirst.getNumber(), argSecond.getNumber(), operator));
        return argFirst;
    }

    private Element calculateDiffLiterals(Literal argFirst, Literal argSecond, OperatorType operator,
                                          List<ImportDirective> imports) throws IncorrectUnitTypeException {
        List<Pair<String, Double>> firstList = Common.getUnitsPair(argFirst.getUnit(), imports);
        List<Pair<String, Double>> secondList = Common.getUnitsPair(argSecond.getUnit(), imports);
        if (firstList == null || secondList == null || firstList.get(0) == null || secondList.get(0) == null
                || firstList.get(1) == null || secondList.get(1) == null
                || !firstList.get(0).getKey().equals(secondList.get(0).getKey()))
            throw new IncorrectUnitTypeException(argSecond.getUnit(), argSecond.getPosition());

        Literal convertFirstArg = new Literal(argFirst.getNumber().getValue() * firstList.get(1).getValue(),
                firstList.get(0).getKey(), null);
        Literal convertSecondArg = new Literal(argSecond.getNumber().getValue() * secondList.get(1).getValue(),
                firstList.get(0).getKey(), null);
        return calculateSameLiterals(convertFirstArg, convertSecondArg, operator);
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
