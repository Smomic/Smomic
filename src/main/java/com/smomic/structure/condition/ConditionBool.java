package com.smomic.structure.condition;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class ConditionBool extends Element{
    private boolean value;

    public ConditionBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public NodeType getType() {
        return NodeType.ConditionBool;
    }

    public void changeSign() {
        value = !value;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return null;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
