package com.smomic.structure.expression;

import com.smomic.structure.FunDefinition;
import com.smomic.structure.Scope;
import com.smomic.structure.element.Element;
import com.smomic.structure.importSt.ImportDirective;

import java.util.List;

public class PrimaryExpression extends Expression {
    private Element element;

    public PrimaryExpression(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }


    @Override
    public Element execute(Scope scope, List<FunDefinition> functions, List<ImportDirective> imports) {
        return element.execute(scope, functions, imports);
    }

    @Override
    public boolean canReturn() {
        return false;
    }
}
