package com.smomic.structure.condition;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.NodeType;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class ParentCondition extends Condition {
    private MainCondition mainCondition;

    public ParentCondition(MainCondition mainCondition) {
        this.mainCondition = mainCondition;
    }

    @Override
    public NodeType getType() {
        return null;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return mainCondition.execute(scope, functions, imports);
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
