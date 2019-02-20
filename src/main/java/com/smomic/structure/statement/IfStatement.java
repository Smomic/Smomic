package com.smomic.structure.statement;

import com.smomic.structure.Body;
import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.condition.ConditionBool;
import com.smomic.structure.condition.MainCondition;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class IfStatement extends Statement {
    private Body ifBody;
    private Body elseBody;
    private MainCondition condition;

    public IfStatement(Body ifBody, Body elseBody, MainCondition condition) {
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.condition = condition;
    }

    public MainCondition getCondition() {
        return condition;
    }

    @Override
    public NodeType getType() {
        return NodeType.IfStatement;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        ConditionBool result = (ConditionBool)condition.execute(scope, functions, imports);
        if (result != null && result.getValue())
            return ifBody.execute(scope, functions, imports);

        else if (elseBody != null)
            return elseBody.execute(scope, functions, imports);

        return null;
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}
