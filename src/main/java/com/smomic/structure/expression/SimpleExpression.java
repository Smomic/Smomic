package com.smomic.structure.expression;

import com.smomic.exception.IncorrectConvertException;
import com.smomic.exception.IncorrectUnitTypeException;
import com.smomic.exception.IncorrectVarNameException;
import com.smomic.structure.*;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.element.Variable;
import com.smomic.structure.importSt.ImportDirective;
import javafx.util.Pair;

import java.util.List;

public class SimpleExpression extends Expression {
    private PrimaryExpression primaryExpression;
    private String convertOp;
    private Pair<Integer, Integer> convertOpPos;

    public SimpleExpression(PrimaryExpression primaryExpression, String convertOp, Pair<Integer, Integer> convertOpPos) {
        this.primaryExpression = primaryExpression;
        this.convertOp = convertOp;
        this.convertOpPos = convertOpPos;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        Element result = primaryExpression.execute(scope, functions, imports);

        if (convertOp != null) {
            if (result.getType().equals(NodeType.Literal)) {
                try {
                    return convert(imports, (Literal) result);
                } catch (IncorrectUnitTypeException e) {
                    e.show();
                } catch (IncorrectConvertException e) {
                    e.show();
                }
            } else if (result.getType().equals(NodeType.Var)) {
                try {
                    Element element = scope.getVariableValue(((Variable) result).getName(),
                            ((Variable) result).getPosition());

                    if (element.getType().equals(NodeType.Literal))
                        return convert(imports, (Literal) element);
                } catch (IncorrectVarNameException e) {
                    e.show();
                } catch (IncorrectUnitTypeException e) {
                    e.show();
                } catch (IncorrectConvertException e) {
                    e.show();
                }
            }
        } else {
            if (result != null && result.getType().equals(NodeType.Var)) {
                try {
                    return Common.getVariableValue(scope, (Variable) result);
                } catch (IncorrectVarNameException e) {
                    e.show();
                }
            }
            return result;
        }

        return null;
    }

    private Literal convert(List<ImportDirective> imports, Literal arg)
            throws IncorrectUnitTypeException, IncorrectConvertException {
        List<Pair<String, Double>> sourceArg = Common.getUnitsPair(arg.getUnit(), imports);
        List<Pair<String, Double>> convertArg = Common.getUnitsPair(convertOp, imports);

        if (sourceArg == null || convertArg == null || sourceArg.get(0) == null || convertArg.get(0) == null
                || sourceArg.get(1) == null || convertArg.get(1) == null) {
            throw new IncorrectUnitTypeException(arg.getUnit(), arg.getPosition());
        }

        if (sourceArg.get(0).getKey().equals(convertArg.get(0).getKey())) {
            Number number = new Number(arg.getNumber().getValue() * sourceArg.get(1).getValue()
                    / convertArg.get(1).getValue(), arg.getPosition());
            arg.setNumber(number);
            arg.setUnit(convertArg.get(1).getKey());
            return arg;
        }

        throw new IncorrectConvertException(convertOp, convertOpPos);
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
