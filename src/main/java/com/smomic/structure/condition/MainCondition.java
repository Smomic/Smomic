package com.smomic.structure.condition;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class MainCondition extends Condition {
    private List<AndCondition> operands;

    public MainCondition(List<AndCondition> operands) {
        this.operands = operands;
    }

    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        Element result = null;
        for (AndCondition operand : operands) {
            result = operand.execute(scope, functions, imports);
            if (result == null || ((ConditionBool) result).getValue())
                return result;
        }
        return result;
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
