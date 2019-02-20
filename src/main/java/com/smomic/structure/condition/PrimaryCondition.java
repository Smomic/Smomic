package com.smomic.structure.condition;

import com.smomic.exception.IncorrectVarNameException;
import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.Common;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Literal;
import com.smomic.structure.element.Number;
import com.smomic.structure.element.Variable;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class PrimaryCondition extends Condition {
    private Element element;
    private boolean isNegation;

    public PrimaryCondition(Element element, boolean isNegation) {
        this.element = element;
        this.isNegation = isNegation;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public NodeType getType() {
        return null;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        Element value = element.execute(scope, functions, imports);

        if (value.getType().equals(NodeType.Var)) {
            try {
                value = Common.getVariableValue(scope, (Variable) element);
            } catch (IncorrectVarNameException e) {
                e.show();
            }
        }

        if (isNegation) {
            if (value.getType().equals(NodeType.ConditionBool)) {
                ((ConditionBool) value).changeSign();
                return value;
            } else if (value.getType().equals(NodeType.Number)) {
                if (((Number) value).getValue().equals(0.0))
                    return new ConditionBool(true);
                else
                    return new ConditionBool(false);
            } else if (value.getType().equals(NodeType.Literal)) {
                if (((Literal) value).getNumber().getValue().equals(0.0))
                    return new ConditionBool(true);
                else
                    return new ConditionBool(false);
            }
        }

        return value;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
