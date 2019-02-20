package com.smomic.structure.expression;

import com.smomic.exception.*;
import com.smomic.structure.*;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.importSt.ImportContent;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.List;

public class MultiExpression extends Expression {
    private List<SimpleExpression> operands;
    private List<OperatorType> operatorList;
    private boolean unaryOp;

    public MultiExpression(List<SimpleExpression> operands, List<OperatorType> operatorList, boolean unaryOp) {
        this.operands = operands;
        this.operatorList = operatorList;
        this.unaryOp = unaryOp;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        Element result = operands.get(0).execute(scope, functions, imports);

        try {
            if (operatorList == null || operatorList.isEmpty()) {
                changeSignIfNeed(result);
                return result;
            }

            for (int i = 1; i < operands.size(); ++i) {
                result = calculateResult(result, operands.get(i).execute(scope, functions, imports),
                        operatorList.get(i - 1), imports);
            }
            return result;
        } catch (IncorrectExpressionTypeException e) {
            e.show();
        } catch (DivideByZeroException e) {
            e.show();
        } catch (IncorrectUnitTypeException e) {
            e.show();
        } catch (IncorrectMultiplyUnitException e) {
            e.show();
        } catch (UndefinedMultiTypeException e) {
            e.show();
        }

        return null;
    }

    private Element calculateResult(Element argFirst, Element argSecond, OperatorType operator,
                                    List<ImportDirective> imports)
            throws IncorrectExpressionTypeException, DivideByZeroException, IncorrectUnitTypeException,
            IncorrectMultiplyUnitException, UndefinedMultiTypeException {

        if (argFirst == null || argSecond == null)
            return null;

        if (argFirst.getType().equals(argSecond.getType())) {
            if (argFirst.getType().equals(NodeType.Number)) {
                return calculateNumbers((Number) argFirst, (Number) argSecond, operator);
            } else
                return calculateLiterals((Literal) argFirst, (Literal) argSecond, operator, imports);
        } else {
            if (argFirst.getType().equals(NodeType.Literal))
                return calculateDiffTypes((Literal) argFirst, (Number) argSecond, operator);
            else
                return calculateInvertDiffTypes((Number) argFirst, (Literal) argSecond, operator);
        }

    }

    private Element calculateDiffTypes(Literal literal, Number number, OperatorType operator) throws DivideByZeroException {
        literal.setNumber((Number) calculateNumbers(literal.getNumber(), number, operator));
        return literal;
    }

    private Element calculateInvertDiffTypes(Number number, Literal literal, OperatorType operator)
            throws IncorrectExpressionTypeException, DivideByZeroException {
        if (operator.equals(OperatorType.Multiply))
            return calculateDiffTypes(literal, number, operator);

        throw new IncorrectExpressionTypeException(number.getPosition());
    }

    private Element calculateNumbers(Number argFirst, Number argSecond, OperatorType operator) throws DivideByZeroException {
        if (operator.equals(OperatorType.Divide)) {
            if (argSecond.getValue().equals(0.0))
                throw new DivideByZeroException(argSecond.getPosition());
            argFirst.setValue(argFirst.getValue() / argSecond.getValue());
        } else if (operator.equals(OperatorType.Multiply)) {
            argFirst.setValue(argFirst.getValue() * argSecond.getValue());
        } else if (operator.equals(OperatorType.Modulo))
            argFirst.setValue(argFirst.getValue() % argSecond.getValue());

        changeSignIfNeed(argFirst);
        return argFirst;
    }

    private Element calculateLiterals(Literal argFirst, Literal argSecond, OperatorType operator,
                                      List<ImportDirective> imports)
            throws DivideByZeroException, IncorrectUnitTypeException,
            IncorrectMultiplyUnitException, UndefinedMultiTypeException {

        List<String> firstList = getMultiUnits(argFirst, imports);
        List<String> secondList = getMultiUnits(argSecond, imports);
        if (firstList == null || secondList == null)
            return null;

        String common = getCommonUnitType(firstList, secondList);
        if (common == null)
            throw new IncorrectMultiplyUnitException(argSecond.getUnit(), argSecond.getPosition());

        List<String> list;
        if (firstList.get(0).equals(secondList.get(0)))
            list = (firstList.size() > secondList.size()) ? firstList : secondList;
        else
            list = (firstList.indexOf(common) > 0) ? firstList : secondList;

        int levelFirst = list.indexOf(getType(argFirst.getUnit(), imports)) + 1;
        int levelSecond = list.indexOf(getType(argSecond.getUnit(), imports)) + 1;

        convertToMainUnit(argFirst, imports);
        convertToMainUnit(argSecond, imports);

        if (!operator.equals(OperatorType.Multiply)) {
            if (levelFirst < levelSecond)
                throw new UndefinedMultiTypeException(argSecond.getPosition());
            else if (levelFirst == levelSecond) {
                return new Number(((Number) calculateNumbers(argFirst.getNumber(),
                        argSecond.getNumber(), operator)).getValue(), null);
            } else {
                argFirst.setNumber((Number) calculateNumbers(argFirst.getNumber(), argSecond.getNumber(), operator));
                String unit = getMainTypeName(list.get(levelFirst - levelSecond - 1), imports);
                if (unit != null) {
                    argFirst.setUnit(unit);
                    return argFirst;
                }
            }


        } else {
            if (levelFirst + levelSecond > list.size())
                throw new UndefinedMultiTypeException(argSecond.getPosition());

            argFirst.setNumber((Number) calculateNumbers(argFirst.getNumber(), argSecond.getNumber(), operator));
            String unit = getMainTypeName(list.get(levelFirst + levelSecond - 1), imports);
            if (unit != null) {
                argFirst.setUnit(unit);
                return argFirst;
            }
        }
        return null;
    }

    private List<String> getMultiUnits(Literal arg, List<ImportDirective> imports)
            throws IncorrectUnitTypeException {
        List<Pair<String, Double>> list = Common.getUnitsPair(arg.getUnit(), imports);
        if (list != null && list.get(0) != null && list.get(1) != null) {
            List<String> units = getMultiUnitsByType(list.get(0).getKey(), imports);
            if (units != null)
                return units;
        }
        throw new IncorrectUnitTypeException(arg.getUnit(), arg.getPosition());
    }

    private List<String> getMultiUnitsByType(String type, List<ImportDirective> imports) {
        for (ImportDirective directive : imports) {
            List<ImportContent> list = directive.execute();
            if (list.get(0).getUnit().equals(type))
                return directive.executeMulti();
        }
        return null;
    }

    private String getCommonUnitType(List<String> firstList, List<String> secondList) {
        List<String> mainList = (firstList.size() > secondList.size()) ? firstList : secondList;
        List<String> checkingList = (mainList == firstList) ? secondList : firstList;

        for (String s : checkingList) {
            if (mainList.contains(s))
                return s;
        }
        return null;
    }

    private String getType(String unit, List<ImportDirective> imports) {
        for (ImportDirective i : imports) {
            List<ImportContent> list = i.execute();
            for (ImportContent content : list) {
                if (content.getUnit().equals(unit)) {
                    return i.executeMulti().get(0);
                }
            }
        }
        return null;
    }

    private static void convertToMainUnit(Literal arg, List<ImportDirective> imports)
            throws IncorrectMultiplyUnitException {
        List<Pair<String, Double>> list = Common.getUnitsPair(arg.getUnit(), imports);
        if (list == null || list.get(0) == null || list.get(1) == null)
            throw new IncorrectMultiplyUnitException(arg.getUnit(), arg.getPosition());

        arg.setNumber(new Number(arg.getNumber().getValue() * list.get(1).getValue(), arg.getPosition()));
        arg.setUnit(list.get(0).getKey());
    }

    private String getMainTypeName(String type, List<ImportDirective> imports) {
        for (ImportDirective directive : imports) {
            List<String> list = directive.executeMulti();
            if (list.get(0).equals(type))
                return directive.execute().get(0).getUnit();
        }
        return null;
    }

    private void changeSignIfNeed(Element result) {
        if (unaryOp) {
            if (result.getType().equals(NodeType.Number))
                ((Number) result).changeSign();

            else if (result.getType().equals(NodeType.Literal))
                ((Literal) result).changeSign();
        }
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
