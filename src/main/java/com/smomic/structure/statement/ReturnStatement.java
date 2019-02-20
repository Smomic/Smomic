package com.smomic.structure.statement;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.expression.MainExpression;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class ReturnStatement extends Statement {
    private MainExpression value;

    public ReturnStatement(MainExpression value) {
        this.value = value;
    }

    public MainExpression getValue() {
        return value;
    }

    @Override
    public NodeType getType() {
        return NodeType.ReturnStatement;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return value.execute(scope, functions, imports);
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}
