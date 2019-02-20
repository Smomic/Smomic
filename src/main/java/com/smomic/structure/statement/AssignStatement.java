package com.smomic.structure.statement;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.element.Variable;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class AssignStatement extends Statement {
    private Variable variable;
    private Element value;

    public AssignStatement(Variable variable, Element value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.AssignStatement;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        scope.addVariable(variable.getName(), value.execute(scope, functions, imports));
        return null;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
