package com.smomic.structure.statement;

import com.smomic.structure.Body;
import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.condition.Condition;
import com.smomic.structure.condition.ConditionBool;
import com.smomic.structure.condition.MainCondition;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class LoopStatement extends Statement {
    private MainCondition condition;
    private Body body;

    public LoopStatement(MainCondition condition, Body body) {
        this.condition = condition;
        this.body = body;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public NodeType getType() {
        return NodeType.LoopStatement;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        while(((ConditionBool)condition.execute(scope, functions, imports)).getValue()) {
            Element result = body.execute(scope, functions, imports);
            if (result != null && body.canReturn())
                return result;
        }

        return null;
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}
